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
package com.app.appellas.data.network.services.user

import com.app.appellas.data.models.dtos.body.*
import com.app.appellas.data.models.dtos.response.GenericResponse
import com.app.appellas.data.models.dtos.response.LoginResponse
import com.app.appellas.data.models.dtos.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequest): Response<GenericResponse<List<LoginResponse>>>

    @POST("auth/signup")
    suspend fun register(@Body requestBody: RegisterBody): Response<GenericResponse<RegisterResponse>>

    @POST("auth/send_code")
    suspend fun sendRecoveryCode(@Body requestBody: SendCodeBody): Response<GenericResponse<String>>

    @POST("auth/verify_code")
    suspend fun verifyCode(@Body requestBody: VerifyCodeBody): Response<GenericResponse<String>>

    @POST("auth/change_password")
    suspend fun changePassword(@Body requestBody: ChangePasswordBody): Response<GenericResponse<String>>

}