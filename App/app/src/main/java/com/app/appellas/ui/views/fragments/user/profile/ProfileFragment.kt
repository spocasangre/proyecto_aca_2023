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
package com.app.appellas.ui.views.fragments.user.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.appellas.AppApplication
import com.app.appellas.R
import com.app.appellas.databinding.ProfileFragmentBinding
import com.app.appellas.viewmodel.ViewModelFactory
import com.app.appellas.viewmodel.admin.AdminUserViewModel

class ProfileFragment : Fragment() {

    private var mBinding: ProfileFragmentBinding? = null
    private val binding get() = mBinding!!

    private val userApp by lazy {
        activity?.application as AppApplication
    }

    private val authViewModel: AdminUserViewModel by viewModels {
        ViewModelFactory(userApp.adminUserRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ProfileFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.userData.observe(viewLifecycleOwner) { user ->
            try {
                binding.profileEmail.text = user[0].mail
                binding.profileName.text = user[0].nombre
            } catch (e: Exception) {
                Log.d("Error", "Error")
            }

            binding.progfileActionDelete.setOnClickListener {
                try {
                    when (user[0].rol) {
                        "usuario" -> {
                            findNavController().navigate(R.id.action_profileFragment_to_dialogDeleteAccount)
                        }
                        "admin" -> {
                            findNavController().navigate(R.id.action_profileFragment2_to_dialogDeleteAccount2)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Error", "Error")
                }
            }
        }

        binding.createBlogActionReturn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}