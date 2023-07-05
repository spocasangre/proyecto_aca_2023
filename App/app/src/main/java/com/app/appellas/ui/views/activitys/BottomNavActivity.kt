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

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.app.appellas.R
import com.app.appellas.databinding.BottomNavActivityBinding
import com.app.appellas.viewmodel.user.HomeViewModel

class BottomNavActivity: AppCompatActivity() {

    private lateinit var mBinding: BottomNavActivityBinding

    private var id: String? = null

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = BottomNavActivityBinding.inflate(layoutInflater)

        id = intent.extras?.getString("id", "")

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        if(id != null) {
            Log.d("IDDATA", id.toString())
            viewModel.goAlertDetail(id.toString())
            viewModel.goAlertDetail.observe(this) {
                it?.let {
                    if(it) {
                        navController.navigate(R.id.alertDetailFragment)
                    }
                }
            }
        }

        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        mBinding.bottomNavMenu.setOnClickListener {
            navController.navigate(R.id.bottomSheetMenuFragment)
        }

        mBinding.bottomNavProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment)
        }

        mBinding.bottomNavActionAlert.setOnClickListener {
            navController.navigate(R.id.dialogAlertFragment)
        }
    }

}