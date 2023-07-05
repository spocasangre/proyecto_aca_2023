package com.app.appellas.data.network.services.user

import com.app.appellas.data.models.dtos.body.CheckPointBody
import com.app.appellas.data.models.dtos.response.CheckPointResponse
import com.app.appellas.data.models.dtos.response.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GeozoneService {

    @POST("geozone/checkpoint")
    suspend fun postCheckPoint(@Header("Authorization") token: String, @Body body: CheckPointBody): GenericResponse<CheckPointResponse>

}