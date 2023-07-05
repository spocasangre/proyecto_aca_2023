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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.app.appellas.databinding.DialogConfirmRegisterBinding
import com.app.appellas.databinding.DialogCreateBlogBinding

class DialogCreateBlog: DialogFragment() {

    private var mBinding: DialogCreateBlogBinding? = null
    private val binding get() = mBinding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogCreateBlogBinding.inflate(inflater, container, false)

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNavigationListener()
    }

    private fun setUpNavigationListener() {
        binding.dialogConfirmRegisterActionOk.setOnClickListener {
            val direction = DialogCreateBlogDirections
                .actionDialogCreateBlogToBlogsFragment()
            val navHost = NavHostFragment.findNavController(this)
            navHost.navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}