package com.app.appellas.ui.views.fragments.user.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.appellas.data.network.UIState
import com.app.appellas.databinding.LoginFragmentBinding
import com.app.appellas.ui.utils.checkNotificationPermission
import com.app.appellas.ui.views.activitys.AdminBottomNavActivity
import com.app.appellas.ui.views.activitys.BottomNavActivity
import com.app.appellas.viewmodel.user.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging

class LoginFragment: Fragment() {

    private var mBinding: LoginFragmentBinding? = null
    private val binding get() = mBinding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var sharedPref: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    activity?.finish()
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
        mBinding = LoginFragmentBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = authViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        sharedPref = requireActivity().getSharedPreferences("appellas", Context.MODE_PRIVATE)!!

        binding.loginEditEmail.doAfterTextChanged { message ->
            authViewModel.email.value = message.toString()
        }

        binding.loginEditPassword.doAfterTextChanged { message ->
            authViewModel.password.value = message.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNavigationListeners()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        observeState()
        goHomeObserver()
        goAdminObserver()

        authViewModel.userInfo.observe(viewLifecycleOwner) { user ->
            with(sharedPref.edit()) {
                putString("token_session", user.accessToken)
                putString("id_user", user.id.toString())
                commit()
            }
        }
    }

    private fun goAdminObserver() {
        authViewModel.goAdminFragment.observe(viewLifecycleOwner) {
            it?.let {
                if(it) {
                    startActivity(Intent(Intent(requireContext(), AdminBottomNavActivity::class.java)))
                    activity?.finish()
                }
                authViewModel.endGoAdminFragment()
            }
        }
    }

    private fun goHomeObserver() {
        authViewModel.goHomeFragment.observe(viewLifecycleOwner) {
            it?.let {
                if(it) {
                    startActivity(Intent(Intent(requireContext(), BottomNavActivity::class.java)))
                    activity?.finish()
                }
                authViewModel.endGoHomeFragment()
            }
        }
    }

    private fun observeState() {
        authViewModel.stateUI.observe(viewLifecycleOwner) { uiState ->
            handleUIState(uiState)
        }
    }

    private fun handleUIState(uiState: UIState<Int>?) {
        //val toastText = layout.findViewById<TextView>(R.id.toast_text)
        //layout.findViewById<ImageView>(R.id.toast_image).visibility = View.GONE
        when(uiState) {
            is UIState.Loading -> {
                showProgressBar()
            }
            is UIState.Success -> {
                endShowProgressBar()
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
        binding.loginProgressBar.visibility = View.GONE
        binding.loginActionLogin.text = "Login"
    }

    private fun showProgressBar() {
        binding.loginProgressBar.visibility = View.VISIBLE
        binding.loginActionLogin.text = ""
    }

    private fun setUpListeners() {
        onLoginClickListener()
    }

    private fun onLoginClickListener() {
        binding.loginActionLogin.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                if (requireContext().checkNotificationPermission()) {
                    getFirebaseToken()
                } else {
                    requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                getFirebaseToken()
            }
        }
    }

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it) {
                getFirebaseToken()
            } else {
                Toast.makeText(requireContext(), "Debe habilitar el permiso de notificaciones", Toast.LENGTH_SHORT).show()
                goSettings()
            }
        }

    private fun goSettings() {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("android.provider.extra.APP_PACKAGE", requireContext().packageName)
        startActivity(intent)
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            } else {
                if (task.result != null) {
                    authViewModel.login(task.result)
                }
            }
        }
    }

    private fun setUpNavigationListeners() {
        onCreateAccountNavigation()
    }

    private fun onCreateAccountNavigation() {
        binding.loginActionRegisterAccount.setOnClickListener {
            val direction = LoginFragmentDirections
                .actionLoginFragmentToCreateAccountFragment()
            val navController = findNavController()
            navController.navigate(direction)
        }

        binding.loginActionForgot.setOnClickListener {
            val direction = LoginFragmentDirections
                .actionLoginFragmentToEnterEmailFragment()
            findNavController().navigate(direction)
        }
    }
}