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
package com.app.appellas.data.network.services.admin

import com.app.appellas.data.models.dtos.body.UpdateLocationBody
import com.app.appellas.data.models.dtos.response.GenericResponse
import com.app.appellas.data.models.dtos.response.LocationResponse
import retrofit2.Response
import retrofit2.http.*

interface AdminLocationServices {

    @GET("location")
    suspend fun getAllLocations(@Header("Authorization") token: String): Response<GenericResponse<List<LocationResponse>>>

    @PUT("location")
    suspend fun updateLocation(@Header("Authorization") token: String, @Body requestBody: List<UpdateLocationBody>): Response<GenericResponse<String>>

    @GET("location/detail/{id}")
    suspend fun getLocationDetail(@Header("Authorization") token: String, @Path("id") id: String): Response<GenericResponse<LocationResponse>>

    @GET("location/detail/maps_id/{id}")
    suspend fun getAlertDetailMapsId(@Header("Authorization") token: String, @Path("id") id: String): Response<GenericResponse<LocationResponse>>

}