package com.github.mathsemilio.desafiobecaluciana.viewModel

import com.github.mathsemilio.desafiobecaluciana.data.Login
import com.github.mathsemilio.desafiobecaluciana.data.Result

interface LoginRepository {
    suspend fun loginUser(login: Login): Result<String>
}