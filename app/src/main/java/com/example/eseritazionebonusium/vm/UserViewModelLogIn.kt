package com.example.eseritazionebonusium.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelLogIn : ViewModel() {
    private var isAdmin = false        //true if admin login

    val isAnAdmin get() = isAdmin

    fun verificaCredenzialiAccesso(username: String, password: String): Int {
        val userExist = DataRepository.getUser(username)

        if (!userExist.username.equals("")) {    //if user exixst
            if (userExist.password.equals(password)) {    //if password is equal to saved login
                DataRepository.salvaUtenteCorrente(userExist)  //save current user in sharedPreferences
                saveStatusIfAdmin(userExist)
                return 0;

            } else { //if pawwsord is wrong
                return -2
            }

        } else {  //if user does not exist
            return -1
        }
    }

    private fun saveStatusIfAdmin(user: User) {
        isAdmin = user.admin == 1
    }
}