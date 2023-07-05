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
package com.app.appellas

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.app.appellas.data.AppEllasDB
import com.app.appellas.data.network.AppAPI
import com.app.appellas.repository.admin.AdminAdvisorRepository
import com.app.appellas.repository.admin.AdminLocationRepository
import com.app.appellas.repository.admin.AdminUserRepository
import com.app.appellas.repository.user.BlogRepository
import com.app.appellas.repository.user.ContactRepository
import com.app.appellas.repository.user.LocationRepository
import com.app.appellas.repository.user.LoginRepository
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private val database by lazy {
        AppEllasDB.getDatabase(this)
    }

    val loginRepository by lazy {
        val userDao = database.userDao()
        LoginRepository(userDao, AppAPI)
    }

    val blogRepository by lazy {
        val userDao = database.userDao()
        BlogRepository(userDao, AppAPI)
    }

    val locationRepository by lazy {
        val userDao = database.userDao()
        LocationRepository(userDao, AppAPI)
    }

    val adminUserRepository by lazy {
        val userDao = database.userDao()
        AdminUserRepository(userDao, AppAPI)
    }

    val adminAdvisorRepository by lazy {
        val userDao = database.userDao()
        AdminAdvisorRepository(userDao, AppAPI)
    }

    val adminLocationRepository by lazy {
        val userDao = database.userDao()
        AdminLocationRepository(userDao, AppAPI)
    }

    val contactRepository by lazy {
        val userDao = database.userDao()
        ContactRepository(userDao, AppAPI)
    }
}