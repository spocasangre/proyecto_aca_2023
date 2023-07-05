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
package com.app.appellas.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.appellas.R
import com.app.appellas.data.models.dtos.body.UpdateLocationBody
import com.app.appellas.data.models.dtos.response.LocationResponse
import com.app.appellas.data.network.AppAPI
import com.app.appellas.data.network.UIState
import com.app.appellas.repository.admin.AdminLocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminLocationViewModel(repository: AdminLocationRepository): ViewModel() {

    val userData = repository.findAllUsers()

    val locationList = MutableLiveData<List<LocationResponse>>()
    val locationDetail = MutableLiveData<LocationResponse>()

    private val _stateUI = MutableLiveData<UIState<Int>?>()
    val stateUI: LiveData<UIState<Int>?> = _stateUI

    fun getAllLocations(token: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                //_stateUI.value = UIState.Loading(R.id.)
                val call = AppAPI.adminLocationServices.getAllLocations(
                    "Bearer $token"
                )
                val response = call.body()
                val errorCode = response?.code ?: 0
                val data = response?.data
                val message = response?.message ?: "Error"

                withContext(Dispatchers.Main) {
                    if(call.isSuccessful) {
                        if(errorCode == 1){
                            //_stateUI.value = UIState.Success
                            //_stateUI.value = null
                            locationList.value = data!!
                            Log.d("SUCCESS", "SUCCESS")
                        } else {
                            //_stateUI.value = UIState.Error(message)
                            //_stateUI.value = null
                            Log.d("Error", message)
                        }
                    } else {
                        Log.d("Error", "Error")
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Error", "Error")
        }
    }

    fun updateLocations(token: String, body: List<UpdateLocationBody>) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                //_stateUI.postValue(UIState.Loading(R.id.admin_information_map_progressBar))

                val call = AppAPI.adminLocationServices.updateLocation(
                    "Bearer $token",
                    body
                )
                val response = call.body()
                val errorCode = response?.code ?: 0
                val message = response?.message ?: "Error"

                withContext(Dispatchers.Main) {
                    if(call.isSuccessful) {
                        if(errorCode == 1) {
                            _stateUI.value = UIState.Success
                            _stateUI.value = null
                        } else {
                            _stateUI.value = UIState.Error(message)
                            _stateUI.value = null
                        }
                    } else {
                        Log.d("Error", "Error")
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Error", "Error")
        }
    }

    fun getLocationDetail(token: String, id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _stateUI.postValue(UIState.Loading(R.id.dialog_marker_information_progressBar))

                val call = AppAPI.adminLocationServices.getAlertDetailMapsId(
                    "Bearer $token",
                    id.toString()
                )
                val response = call.body()
                val errorCode = response?.code ?: 0
                val data = response?.data
                val message = response?.message ?: "Error"

                withContext(Dispatchers.Main) {
                    if(call.isSuccessful) {
                        if(errorCode == 1) {
                            locationDetail.value = data!!
                            _stateUI.value = UIState.Success
                            _stateUI.value = null
                        } else {
                            _stateUI.value = UIState.Error(message)
                            _stateUI.value = null
                        }
                    } else {
                        Log.d("Error", "Error")
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Error", "Error")
        }
    }

}