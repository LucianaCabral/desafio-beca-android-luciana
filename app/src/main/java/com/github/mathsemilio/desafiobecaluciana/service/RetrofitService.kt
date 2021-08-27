package com.github.mathsemilio.desafiobecaluciana.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        private const val BASE_URL = "http://ec2-user@ec2-3-84-35-12.compute-1.amazonaws.com/"
    }

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val loginApi: LoginApi
        get() = retrofit.create(LoginApi::class.java)
}
