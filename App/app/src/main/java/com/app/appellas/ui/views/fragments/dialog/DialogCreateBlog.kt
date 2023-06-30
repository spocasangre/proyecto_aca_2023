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