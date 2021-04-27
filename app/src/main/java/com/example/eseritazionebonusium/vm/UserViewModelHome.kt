package com.example.eseritazionebonusium.vm

import androidx.lifecycle.ViewModel

class UserViewModelHome : ViewModel() {
    private var isAdmin = false        //true if admin login

    val isAnAdmin get() = isAdmin
}