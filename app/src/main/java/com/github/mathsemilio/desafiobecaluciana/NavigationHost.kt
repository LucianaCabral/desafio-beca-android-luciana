package com.github.mathsemilio.desafiobecaluciana

import androidx.fragment.app.Fragment

interface NavigationHost {
    fun navigation(fragment: Fragment, addToBackstack : Boolean)
}