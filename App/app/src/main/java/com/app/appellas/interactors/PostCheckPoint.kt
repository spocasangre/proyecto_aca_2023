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
package com.app.appellas.interactors

import android.util.Log
import com.app.appellas.data.models.DataState
import com.app.appellas.data.models.dtos.body.CheckPointBody
import com.app.appellas.data.models.dtos.body.CreateLocationBody
import com.app.appellas.data.models.dtos.response.CheckPointResponse
import com.app.appellas.data.models.dtos.response.CreateLocationResponse
import com.app.appellas.data.models.dtos.response.GenericResponse
import com.app.appellas.data.network.services.WidgetService
import com.app.appellas.data.network.services.user.GeozoneService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostCheckPoint(
    private val service: GeozoneService
) {

    fun execute(
        token: String,
        body: CheckPointBody,
        isNetworkAvailable: Boolean
    ): Flow<DataState<GenericResponse<CheckPointResponse>>> = flow{

        try {

            emit(DataState.loading<GenericResponse<CheckPointResponse>>())

            // just to show pagination/progress because api is fast
            delay(1000)

            if(isNetworkAvailable){

                val networkRecipe = getAsignadaFromNetwork(body, token)


                val list = networkRecipe.data

                /* if(networkRecipe.errorCode != 1){

                     throw Exception(networkRecipe.message,)

                 }else{*/
                val g = GenericResponse<CheckPointResponse>(
                    networkRecipe.isSuccess,
                    networkRecipe.code,
                    list,
                    networkRecipe.message
                )
                emit(DataState.succes(g))

                //}
            }
        }catch (e: Exception){

            emit(DataState.error<GenericResponse<CheckPointResponse>>("${e.message}"))
        }
    }

    private suspend fun getAsignadaFromNetwork(body: CheckPointBody, token: String): GenericResponse<CheckPointResponse> {
        return service.postCheckPoint(
            "Bearer $token",
            body
        )
    }

}