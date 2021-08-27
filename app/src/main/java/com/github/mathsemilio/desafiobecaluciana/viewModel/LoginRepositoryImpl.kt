package com.github.mathsemilio.desafiobecaluciana.viewModel

import com.github.mathsemilio.desafiobecaluciana.data.Login
import com.github.mathsemilio.desafiobecaluciana.data.LoginDataSource
import com.github.mathsemilio.desafiobecaluciana.data.Result

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource) : LoginRepository {
    override suspend fun loginUser(login: Login): Result<String>
    {
        return loginDataSource.loginUser(login)
    }
}