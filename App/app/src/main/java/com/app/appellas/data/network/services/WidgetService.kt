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
package com.app.appellas.data.network.services

import com.app.appellas.data.models.dtos.body.CreateLocationBody
import com.app.appellas.data.models.dtos.response.CreateLocationResponse
import com.app.appellas.data.models.dtos.response.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface WidgetService {

    @POST("location")
    suspend fun createLocation(@Header("Authorization") token: String, @Body locationBody: CreateLocationBody): GenericResponse<CreateLocationResponse>

}