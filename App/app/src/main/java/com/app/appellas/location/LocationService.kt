/*
  Copyright 2023 WeGotYou!

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package com.app.appellas.location

import android.app.Notification
import android.app.Notification.VISIBILITY_SECRET
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        val notification = NotificationCompat.Builder(applicationContext, "location")
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
                                        Log.d("SendNotification", "1")
                                        Notification.instance?.addOrder("1")
                                        sendNotification(data.message.toString())
                                    }
                                    sharedPref.edit().putBoolean("isInside", true).apply()
                                }
                                7 -> {
                                    if(sharedPref.getBoolean("isInside", false)) {
                                        Log.d("SendNotification", "7")
                                        Notification.instance?.addOrder("7")
                                        sendNotification(data.message.toString())
                                    }
                                    sharedPref.edit().putBoolean("isInside", false).apply()
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
        val channel = NotificationChannel("fence", "fence", NotificationManager.IMPORTANCE_HIGH).apply {
            description = "fence"
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, "fence")
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Alerta de zona!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .build()

        val notificationManager2 = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager2.notify(/*notificationId*/ 2, notification)
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    class Notification {
        private val newOrder: MutableLiveData<String>
        private val mensaje: MutableLiveData<String>

        fun getNewOrder(): LiveData<String> {
            return newOrder
        }

        fun addOrder(orderID: String) {
            newOrder.postValue(orderID)
        }

        fun getMensaje(): LiveData<String> {
            return mensaje
        }

        fun addMensaje(orderID: String) {
            mensaje.postValue(orderID)
        }

        companion object {
            var instance: Notification? = null
                get() {
                    if (field == null) {
                        field = Notification()
                    }
                    return field
                }
                private set
        }

        init {
            newOrder = MutableLiveData()
            mensaje = MutableLiveData()
        }
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

}