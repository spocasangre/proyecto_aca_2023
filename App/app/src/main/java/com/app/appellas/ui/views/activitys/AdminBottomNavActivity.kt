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
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.app.appellas.R
import com.app.appellas.databinding.AdminBottomNavActivityBinding
import com.app.appellas.databinding.BottomNavActivityBinding

class AdminBottomNavActivity: AppCompatActivity() {

    private lateinit var mBinding: AdminBottomNavActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = AdminBottomNavActivityBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_admin_nav) as NavHostFragment

        val navController = navHostFragment.navController

        mBinding.bottomNavMenu.setOnClickListener {
            navController.navigate(R.id.adminBottomSheetMenuFragment)
        }

        mBinding.bottomNavProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment2)
        }
    }

}