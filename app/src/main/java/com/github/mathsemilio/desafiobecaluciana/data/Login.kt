package com.github.mathsemilio.desafiobecaluciana.data

import com.google.gson.annotations.SerializedName

data class Login(
    val user: String,
    @SerializedName("pass")
    val password: String
)