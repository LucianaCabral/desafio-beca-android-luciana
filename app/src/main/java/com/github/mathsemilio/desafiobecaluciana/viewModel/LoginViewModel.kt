package com.github.mathsemilio.desafiobecaluciana.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mathsemilio.desafiobecaluciana.data.Login
import com.github.mathsemilio.desafiobecaluciana.data.Result
import kotlinx.coroutines.launch


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    sealed class LoginViewState {
        data class Success(val token: String) : LoginViewState()
        object Failed : LoginViewState()
    }

    private val _viewState = MutableLiveData<LoginViewState>()
    val viewState: LiveData<LoginViewState>
        get() = _viewState

    fun login(login: Login) {
        viewModelScope.launch {
            loginRepository.loginUser(login).also { result ->
                when (result) {
                    is Result.Completed ->
                        _viewState.value = LoginViewState.Success(token = "")
                    is Result.Failed ->
                        _viewState.value = LoginViewState.Success(token = "")
                }
            }
        }
    }
}