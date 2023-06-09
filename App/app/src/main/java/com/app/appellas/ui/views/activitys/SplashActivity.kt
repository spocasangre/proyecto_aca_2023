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
package com.app.appellas.ui.views.activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.appellas.AppApplication
import com.app.appellas.databinding.SplashActivityBinding
import com.app.appellas.viewmodel.ViewModelFactory
import com.app.appellas.viewmodel.user.AuthViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: SplashActivityBinding

    private val userApp by lazy {
        application as AppApplication
    }

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(userApp.loginRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initTimer()
    }

    private fun initTimer() {
        Handler().postDelayed({
            authViewModel.userData.observe(this) {
                if (it.isEmpty()) {
                    startActivity(Intent(Intent(this, MainActivity::class.java)))
                    finish()
                } else {
                    if(it[0].rol == "usuario") {
                        val intent = (Intent(this, BottomNavActivity::class.java))
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    } else if(it[0].rol == "admin") {
                        val intent = (Intent(this, AdminBottomNavActivity::class.java))
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }
                }
                finish()
            }
        }, 2000)
    }

}