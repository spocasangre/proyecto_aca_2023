package com.app.appellas.location

import android.app.Notification
import android.app.Notification.VISIBILITY_SECRET
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.app.appellas.R
import com.app.appellas.data.models.dtos.body.CheckPointBody
import com.app.appellas.data.network.AppAPI
import com.app.appellas.interactors.PostCheckPoint
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private lateinit var sharedPref: SharedPreferences
    private var token: String? = null

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val postCheckPoint = PostCheckPoint(AppAPI.geozoneService)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

        sharedPref = applicationContext.getSharedPreferences("appellas", Context.MODE_PRIVATE)
        token = sharedPref.getString("token_session", "")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        Log.d("LocationService", "start")
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Enviando ubicacion...")
            .setContentText("Ubicacion: null")
            .setSmallIcon(R.drawable.ic_logo)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient.getLocationUpdates(30000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                val lat = location.latitude.toString()
                val lon = location.longitude.toString()
                val updateNotification = notification.setContentText(
                    "Ubicacion: ($lat, $lon)"
                )
                notificationManager.notify(1, updateNotification.build())
                with(sharedPref.edit()) {
                    putString("latitude", lat)
                    putString("longitude", lon)
                    apply()
                }
                val body = CheckPointBody(
                    lat,
                    lon
                )

                scope.launch {
                    postCheckPoint.execute(token.toString(), body, true).onEach { dataState ->

                        dataState.loading?.let { isload ->
                            Log.d("LocationService", "fetchData loading: ${isload}")
                            /*if(isload){
                                progressDialog.show()
                            }else{
                                progressDialog.dismiss()
                            }*/
                        }

                        dataState.error?.let { error ->
                            Log.e("LocationService", "fetchData error: ${error}")
                            //messageToast("Problemas con la conexión")
                        }

                        dataState.data?.let { data ->
                            Log.d("LocationService", "fetchData background : ${data}")
                            when(data.code) {
                                1 -> {
                                    if(!sharedPref.getBoolean("isInside", false)) {
                                        sendNotification(data.message.toString())
                                    }
                                    sharedPref.edit().putBoolean("isInside", true)
                                }
                                7 -> {
                                    if(sharedPref.getBoolean("isInside", false)) {
                                        sendNotification(data.message.toString())
                                    }
                                    sharedPref.edit().putBoolean("isInside", true)
                                }
                            }
                        }
                    }.launchIn(scope)
                }
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun sendNotification(message: String) {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Alerta de zona!")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_logo)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notification)
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

}