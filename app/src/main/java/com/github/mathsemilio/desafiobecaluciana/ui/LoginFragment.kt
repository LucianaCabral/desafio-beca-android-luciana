package com.github.mathsemilio.desafiobecaluciana.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mathsemilio.desafiobecaluciana.NavigationHost
import com.github.mathsemilio.desafiobecaluciana.R
import com.github.mathsemilio.desafiobecaluciana.data.Login
import com.github.mathsemilio.desafiobecaluciana.data.LoginDataSource
import com.github.mathsemilio.desafiobecaluciana.databinding.FragmentLoginBinding
import com.github.mathsemilio.desafiobecaluciana.extensions.getString
import com.github.mathsemilio.desafiobecaluciana.service.RetrofitService
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginRepositoryImpl
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginViewModel
import com.github.mathsemilio.desafiobecaluciana.viewModel.LoginViewModelFactory


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navigationHost: NavigationHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        navigationHost = requireActivity() as NavigationHost
        setupViewModel()
        setupObservers()
        attachLoginButtonOnClickListener()
    }

    override fun onStart() {
        super.onStart()
        loadingVisibility(true)
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
        Log.d(TAG, "onLoginSucess")
        Toast.makeText(context, "Login com sucesso", Toast.LENGTH_SHORT).show()
    }

    private fun onLoginError() {
        binding.progressBar.visibility = View.GONE
        Log.d(TAG, "onLoginError")
        Toast.makeText(context, "Falha ao autenticar", Toast.LENGTH_SHORT).show()
    }

    private fun attachLoginButtonOnClickListener() {
        binding.loginButton.setOnClickListener {
        }
    }

    private fun onLoginButtonClicked() {
        val user = binding.user.getString()
        val password = binding.password.getString()
        loginViewModel.login(Login(user, password))
    }

    private fun loadingVisibility(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }
}