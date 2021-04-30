package com.example.eseritazionebonusium.home

import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelHome : ViewModel() {
    private var isAdmin = false        //true if admin login

    val isAnAdmin get() = isAdmin

    fun getCurrentUser() : User?{
        return DataRepository.getCurrentUser()
    }
}