package com.github.mathsemilio.desafiobecaluciana.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mathsemilio.desafiobecaluciana.NavigationHost
import com.github.mathsemilio.desafiobecaluciana.R
import com.github.mathsemilio.desafiobecaluciana.data.Login
import com.github.mathsemilio.desafiobecaluciana.data.LoginDataSource
import com.github.mathsemilio.desafiobecaluciana.databinding.FragmentLoginBinding
import com.github.mathsemilio.desafiobecaluciana.extensions.getString
import com.github.mathsemilio.desafiobecaluciana.service.RetrofitService
import com.github.mathsemilio.desafiobecaluciana.showToast
import com.github.mathsemilio.desafiobecaluciana.utils.SharedPreferencesUtils
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginRepositoryImpl
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginViewModel
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navigationHost: NavigationHost
    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        navigationHost = requireActivity() as NavigationHost
        sharedPreferencesUtils = SharedPreferencesUtils(requireContext())
        setupViewModel()
        setupObservers()
        attachButtonUseBiometricListener()
        attachLoginButtonOnClickListener()
        setupRememberMeCheckBox()
    }

    private fun setupRememberMeCheckBox() {
        val isRememberMeSet = sharedPreferencesUtils.rememberMeState
        if (isRememberMeSet) {
            binding.rememberMeCheckbox.isChecked = true
            binding.user.editText?.setText(sharedPreferencesUtils.user)
            binding.password.editText?.setText(sharedPreferencesUtils.password)
        } else {
            binding.rememberMeCheckbox.isChecked = false
        }
    }

    private fun onCheckboxClicked() {
        if (binding.rememberMeCheckbox.isChecked) {
            sharedPreferencesUtils.apply {
                user = binding.user.getString()
                password = binding.password.getString()
                rememberMeState = true
            }
        } else {
            sharedPreferencesUtils.rememberMeState = false
            sharedPreferencesUtils.clearLoginData()
        }
    }

    private fun setupViewModel() {
        val retrofitService = RetrofitService()
        val loginDataSource = LoginDataSource(retrofitService.loginApi)
        val loginRepository = LoginRepositoryImpl(loginDataSource)
        val viewModelFactory = LoginViewModelFactory(loginRepository)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }

    private fun setupObservers() {
        loginViewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is LoginViewModel.LoginViewState.Success -> onLoginSuccess()
                LoginViewModel.LoginViewState.Failed -> onLoginError()
            }
        })
    }

    private fun onLoginSuccess() {
        binding.progressBar.visibility = View.GONE
        showToast(getString(R.string.message_login_sucess))
        sharedPreferencesUtils.apply {
            user = binding.user.getString()
            password = binding.password.getString()
        }
        onCheckboxClicked()
    }

    private fun onLoginError() {
        binding.progressBar.visibility = View.GONE
        showToast(getString(R.string.login_failed))
    }

    private fun attachLoginButtonOnClickListener() {
        binding.loginButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            onLoginButtonClicked()
        }
    }

    private fun onLoginButtonClicked() {
        val user = binding.user.getString()
        val password = binding.password.getString()
        loginViewModel.login(Login(user, password))
    }

    private fun attachButtonUseBiometricListener() {
        binding.useBiometricsButton.setOnClickListener {
            showLoginOptionsDialog()
        }
    }

    private fun showLoginOptionsDialog() {
        val materialAlertDialogBuilder =
            MaterialAlertDialogBuilder(
                requireContext(),
                R.style.ThemeOverlay_AppCompat_Dialog_Alert
            )
        materialAlertDialogBuilder
            .setTitle(resources.getString(R.string.label_biometric))
            .setMessage(resources.getString(R.string.label_touch_in_digital_sensor_biometric_to_confirm_biometric))
            .setIcon(R.drawable.ic_fingerprint_)
            .setNegativeButton(resources.getString(R.string.signInPassword)) { dialog, which ->
                showToast(getString(R.string.dialog_button_cancel))
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                showToast(getString(R.string.touch_digital_sensor))
            }
        materialAlertDialogBuilder.show()
    }
}