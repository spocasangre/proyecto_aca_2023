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
package com.app.appellas.ui.utils

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
import java.io.IOException

class ConvertBase64 {

    fun uriToBase64(uri: Uri, resolver: ContentResolver): String {
        var encodedBase64: String = ""
        try {
            val bytes: ByteArray = readBytes(uri, resolver)
            encodedBase64 = Base64.encodeToString(bytes, 0)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return encodedBase64
    }

    @Throws(IOException::class)
    private fun readBytes(uri: Uri, resolver: ContentResolver): ByteArray {
        // this dynamically extends to take the bytes you read
        return resolver.openInputStream(uri)?.readBytes() ?: byteArrayOf()
    }

}