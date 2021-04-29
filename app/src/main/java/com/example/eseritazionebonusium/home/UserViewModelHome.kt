package com.example.eseritazionebonusium.home

import androidx.lifecycle.ViewModel

class UserViewModelHome : ViewModel() {
    private var isAdmin = false        //true if admin login

    val isAnAdmin get() = isAdmin
}