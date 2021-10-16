package com.github.mathsemilio.desafiobecaluciana

import android.widget.Toast
import androidx.fragment.app.Fragment

interface NavigationHost {
    fun navigation(fragment: Fragment, addToBackstack : Boolean)
}

fun Fragment.showToast(string: String) {
    Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
}