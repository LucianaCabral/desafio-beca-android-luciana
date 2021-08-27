package com.github.mathsemilio.desafiobecaluciana.data

import com.github.mathsemilio.desafiobecaluciana.service.LoginApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginDataSource(private val loginApi: LoginApi) {

    suspend fun loginUser(login: Login): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = loginApi.loginUser(login)
                Result.Completed(data = response.body()?.token)
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message)
            }
        }
    }
}