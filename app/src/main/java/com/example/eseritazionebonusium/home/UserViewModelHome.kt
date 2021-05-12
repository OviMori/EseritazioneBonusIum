package com.example.eseritazionebonusium.home

import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelHome : ViewModel() {
    var isAdmin = false        //true if admin login

    init{
        val currentUser = DataRepository.getCurrentUser()
        isAdmin = currentUser != null && DataRepository.isAdmin(currentUser)
    }

    fun getCurrentUser() : User?{
        return DataRepository.getCurrentUser()
    }
}