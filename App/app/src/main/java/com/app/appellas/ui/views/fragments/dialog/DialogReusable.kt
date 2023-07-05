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

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.app.appellas.databinding.DialogReusableBinding

class DialogReusable: DialogFragment() {

    private var mBinding: DialogReusableBinding? = null
    private val binding get() = mBinding!!

    private var listener: AceptarDialogListener? = null

    private lateinit var message: String

    interface AceptarDialogListener {
        fun onAceptar(dialog: Dialog)
    }

    fun setAceptarListener(listener: AceptarDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogReusableBinding.inflate(inflater, container, false)
        
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)

        message = arguments?.getString(MESSAGE_KEY, "").toString()

        binding.title.text = message
      
        binding.btnAceptar.setOnClickListener {
            listener?.onAceptar(dialog!!)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        const val MESSAGE_KEY = "dialog_message"
    }

}