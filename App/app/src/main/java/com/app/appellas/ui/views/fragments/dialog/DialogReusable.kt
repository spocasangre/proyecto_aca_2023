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