package com.github.mathsemilio.desafiobecaluciana.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils(context: Context) {

    private var sharedPreferences = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var user
        get() = sharedPreferences.getString(USER_KEY, "")
        set(value) {
            editor.apply {
                editor.putString(USER_KEY, value)
                apply()
            }
        }

    var password
        get() = sharedPreferences.getString(PASSWORD_KEY, "")
        set(value) {
            editor.apply() {
                editor.putString(PASSWORD_KEY, value)
                apply()
            }
        }

    var rememberMeState: Boolean
        get() = sharedPreferences.getBoolean(REMEMBER_ME_STATE, false)
        set(value) {
            editor.apply {
                editor.putBoolean(REMEMBER_ME_STATE, value)
                apply()
            }
        }

    fun clearLoginData() {
        editor.apply {
            editor.putString(USER_KEY, "")
            editor.putString(PASSWORD_KEY, "")
            apply()
        }
    }

    companion object {
        private const val KEY_NAME = "com.github.mathsemilio.desafiobecaluciana"
        private const val USER_KEY = "user"
        private const val PASSWORD_KEY = "password"
        private const val REMEMBER_ME_STATE = "remember_me_state"
    }
}