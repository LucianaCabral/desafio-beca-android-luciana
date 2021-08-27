package com.github.mathsemilio.desafiobecaluciana.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getString() = this.editText?.text.toString()