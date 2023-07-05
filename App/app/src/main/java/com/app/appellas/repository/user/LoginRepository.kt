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
package com.app.appellas.repository.user

import com.app.appellas.data.dao.UserDao
import com.app.appellas.data.models.entities.User
import com.app.appellas.data.network.AppAPI

class LoginRepository(private val userDao: UserDao, private val appApi: AppAPI) {

    //Funciones del usuario
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun deleteUser() = userDao.deleteUsers()
    fun searchUser(user: Long) = userDao.searchUser(user)
    fun findAllUsers() = userDao.findAllUsers()

}