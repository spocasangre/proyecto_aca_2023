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
package com.app.appellas.data.models.dtos.response

data class BlogsResponse(
    val idBlog: Int,
    val titulo: String,
    val subtitulo: String,
    val descripcion: String,
    val user: UserResponse,
    val imagenes: List<ImageDetail>
)

data class UserResponse(
    val id: Int,
    val nombre: String,
    val telefono: Int,
    val correo: String
)

data class ImageDetail(
    val id_imagen: Int,
    val src: String
)