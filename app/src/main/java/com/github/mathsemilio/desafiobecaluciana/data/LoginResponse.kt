package com.github.mathsemilio.desafiobecaluciana.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    var status: String,
    var code: Int,
    var token: String,
    @SerializedName("msg")
    var message: String,
    @SerializedName("nome")
    var name: String?,
    var email: String,
    @SerializedName("telefone")
    var phone: String?
)

