package com.github.mathsemilio.desafiobecaluciana.service

import com.github.mathsemilio.desafiobecaluciana.data.Login
import com.github.mathsemilio.desafiobecaluciana.data.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    companion object {
        private const val LOGIN_ENDPOINT = "api/mobile/auth/login"
        private const val LOGIN_AUTHORIZATION = "Basic YXNrZ2ZrZGtsZmpzbGtmajpzZGZzNDUxZHM1ZnNkcw=="
    }

    @Headers(LOGIN_AUTHORIZATION)
    @POST(LOGIN_ENDPOINT)
    suspend fun loginUser(@Body login: Login): Response<LoginResponse>
}