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
package com.app.appellas.ui.views.fragments.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.appellas.AppApplication
import com.app.appellas.R
import com.app.appellas.data.network.UIState
import com.app.appellas.databinding.CreateContactFragmentBinding
import com.app.appellas.viewmodel.ViewModelFactory
import com.app.appellas.viewmodel.user.BlogViewModel
import com.app.appellas.viewmodel.user.ContactViewModel

class CreateContactFragment: Fragment() {

    private var mBinding: CreateContactFragmentBinding? = null
    private val binding get() = mBinding!!

    private val userApp by lazy {
        activity?.application as AppApplication
    }

    private val contactViewModel: ContactViewModel by viewModels {
        ViewModelFactory(userApp.contactRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CreateContactFragmentBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = contactViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        binding.createContactNameEdit.doAfterTextChanged { message ->
            contactViewModel.name.value = message.toString()
        }

        binding.createBlogLastnameEdit.doAfterTextChanged { message ->
            contactViewModel.lastName.value = message.toString()
        }

        binding.createBlogNumberEdit.doAfterTextChanged { message ->
            contactViewModel.number.value = message.toString()
        }

        binding.createBlogEmailEdit.doAfterTextChanged { message ->
            contactViewModel.email.value = message.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        observeState()
    }

    private fun observeState() {
        contactViewModel.stateUI.observe(viewLifecycleOwner) { uiState ->
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
                val direction = CreateContactFragmentDirections
                    .actionCreateContactFragmentToContactsFragment()
                findNavController().navigate(direction)
                Log.d("SUccess", "SUCCESS")
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
        binding.createContactProgressBar.visibility = View.GONE
        binding.createBlogActionShare.setTextColor(resources.getColor(R.color.secondary_color))
        binding.createBlogActionShare.isClickable = true
    }

    private fun showProgressBar() {
        binding.createContactProgressBar.visibility = View.VISIBLE
        binding.createBlogActionShare.setTextColor(resources.getColor(android.R.color.transparent))
        binding.createBlogActionShare.isClickable = false
    }

    private fun setUpListeners() {
        binding.createBlogActionShare.setOnClickListener {
            if(binding.createContactNameEdit.text.isNullOrEmpty() || binding.createBlogLastnameEdit.text.isNullOrEmpty() ||
                    binding.createBlogNumberEdit.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                contactViewModel.userData.observe(viewLifecycleOwner) { user ->
                    contactViewModel.createContact(user[0].accessToken, user[0].id)
                }
            }
        }

        binding.createBlogActionReturn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}