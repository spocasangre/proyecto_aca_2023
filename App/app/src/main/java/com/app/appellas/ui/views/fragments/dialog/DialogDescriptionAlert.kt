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
package com.app.appellas.ui.views.fragments.dialog

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.appellas.AppApplication
import com.app.appellas.R
import com.app.appellas.data.network.UIState
import com.app.appellas.databinding.DialogAlertBinding
import com.app.appellas.databinding.DialogDeleteAccountBinding
import com.app.appellas.databinding.DialogDeleteAdvisorBinding
import com.app.appellas.databinding.DialogDescriptionAlertBinding
import com.app.appellas.ui.views.activitys.MainActivity
import com.app.appellas.viewmodel.user.LocationViewModel
import com.app.appellas.viewmodel.ViewModelFactory
import com.app.appellas.viewmodel.admin.AdminAdvisorViewModel
import com.app.appellas.viewmodel.admin.AdminUserViewModel
import com.app.appellas.viewmodel.user.AuthViewModel
import com.google.android.gms.location.*

class DialogDescriptionAlert: DialogFragment() {

    private var mBinding: DialogDescriptionAlertBinding? = null
    private val binding get() = mBinding!!

    private val args: DialogDescriptionAlertArgs by navArgs()

    private val userApp by lazy {
        activity?.application as AppApplication
    }

    private val locationViewModel: LocationViewModel by viewModels {
        ViewModelFactory(userApp.locationRepository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnKeyListener { _: DialogInterface, keyCode: Int, keyEvent: KeyEvent ->
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {

                    val direction = DialogDescriptionAlertDirections
                        .actionDialogDescriptionAlertToHomeFragment()
                    NavHostFragment.findNavController(this@DialogDescriptionAlert).navigate(direction)

                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogDescriptionAlertBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = locationViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        binding.dialogDescriptionAlertTitleEdit.doAfterTextChanged { message ->
            locationViewModel.titleAlert.value = message.toString()
        }

        binding.createReportDescriptionEdit.doAfterTextChanged { message ->
            locationViewModel.descriptionAlert.value = message.toString()
        }

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigationListeners()
        setUpObservers()
    }

    private fun setNavigationListeners() {
        binding.dialogConfirmRegisterActionCancel.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.homeFragment)
        }

        try {
            locationViewModel.userData.observe(viewLifecycleOwner) { user ->
                binding.dialogConfirmRegisterActionOk.setOnClickListener {
                    locationViewModel.updateLocation(user[0].accessToken, args.id)
                }
            }
        } catch (e: Exception){
            Log.d("Error", "Error")
        }
    }

    private fun setUpObservers() {
        observeState()
    }

    private fun observeState() {
        locationViewModel.stateUI.observe(viewLifecycleOwner) { uiState ->
            handleUIState(uiState)
        }
    }

    private fun handleUIState(uiState: UIState<Int>?) {
        when(uiState) {
            is UIState.Loading -> {
                showProgressBar()
            }
            is UIState.Success -> {
                endShowProgressBar()
                NavHostFragment.findNavController(this).navigate(R.id.homeFragment)
            }
            is UIState.Error -> {
                endShowProgressBar()
                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                //activity?.applicationContext?.let { showToast.showToast(it, layout, toastText, uiState.message) }
            }
            else -> {

            }
        }
    }

    private fun endShowProgressBar() {
        binding.dialogDescriptionAlertProgressBar.visibility = View.GONE
        binding.dialogAlertContainer.alpha = 1F
    }

    private fun showProgressBar() {
        binding.dialogDescriptionAlertProgressBar.visibility = View.VISIBLE
        binding.dialogAlertContainer.alpha = 0.4F
    }

}