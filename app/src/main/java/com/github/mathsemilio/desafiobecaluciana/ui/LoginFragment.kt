package com.github.mathsemilio.desafiobecaluciana.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mathsemilio.desafiobecaluciana.NavigationHost
import com.github.mathsemilio.desafiobecaluciana.R
import com.github.mathsemilio.desafiobecaluciana.data.Login
import com.github.mathsemilio.desafiobecaluciana.data.LoginDataSource
import com.github.mathsemilio.desafiobecaluciana.databinding.FragmentLoginBinding
import com.github.mathsemilio.desafiobecaluciana.extensions.getString
import com.github.mathsemilio.desafiobecaluciana.service.RetrofitService
import com.github.mathsemilio.desafiobecaluciana.utils.SharedPreferencesUtils
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginRepositoryImpl
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginViewModel
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
        attachLoginButtonOnClickListener()
        attachButtonUseBiometricListener()

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
        Toast.makeText(requireContext(), getString(R.string.login_sucess), Toast.LENGTH_SHORT)
            .show()
        sharedPreferencesUtils.apply {
            user = binding.user.getString()
            password = binding.password.getString()
        }
    }

    private fun onLoginError() {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(requireContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT)
            .show()

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
                Toast.makeText(requireContext(), getString(R.string.cancel), Toast.LENGTH_SHORT)
                    .show()
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                Toast.makeText(
                    requireContext(),
                    getString(R.string.touch_digital_sensor),
                    LENGTH_LONG
                ).show()
            }
        materialAlertDialogBuilder.show()
    }
}