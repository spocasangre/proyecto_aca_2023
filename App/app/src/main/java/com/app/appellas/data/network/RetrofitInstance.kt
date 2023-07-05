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
package com.app.appellas.data.network

import com.app.appellas.data.network.services.WidgetService
import com.app.appellas.data.network.services.admin.AdminAdvisoryService
import com.app.appellas.data.network.services.admin.AdminLocationServices
import com.app.appellas.data.network.services.admin.AdminUserService
import com.app.appellas.data.network.services.user.BlogService
import com.app.appellas.data.network.services.user.ContactService
import com.app.appellas.data.network.services.user.GeozoneService
import com.app.appellas.data.network.services.user.LocationService
import com.app.appellas.data.network.services.user.LoginService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val API_BASE_URL = "http://api-app-ellas.us-east-1.elasticbeanstalk.com/api/"

private var interceptor =HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

object AppAPI {
    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val blogService: BlogService = retrofit.create(BlogService::class.java)
    val locationService: LocationService = retrofit.create(LocationService::class.java)
    val contactService: ContactService = retrofit.create(ContactService::class.java)
    val adminUserServices: AdminUserService = retrofit.create(AdminUserService::class.java)
    val adminAdvisorServices: AdminAdvisoryService = retrofit.create(AdminAdvisoryService::class.java)
    val adminLocationServices: AdminLocationServices = retrofit.create(AdminLocationServices::class.java)
    val widgetService: WidgetService = retrofit.create(WidgetService::class.java)
    val geozoneService: GeozoneService = retrofit.create(GeozoneService::class.java)
}

/*const val API_BASE_URL = "http://api-subastas.latmobile.com:8080/api/"

object RetrofitInstance {
    private val interceptorLogging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private var token = ""

    fun setToken(value: String) {
        token = value
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(
            OkHttpClient()
                .newBuilder()
                .addInterceptor { chain ->
                    chain.proceed(
                        chain
                            .request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                    )
                }.addInterceptor(interceptorLogging)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getServices(): AuctionsService {
        return retrofit.create(AuctionsService::class.java)
    }
}*/