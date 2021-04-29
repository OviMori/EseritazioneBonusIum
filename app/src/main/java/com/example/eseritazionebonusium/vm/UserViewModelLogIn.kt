package com.example.eseritazionebonusium.vm

import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelLogIn : ViewModel() {
    private var isAdmin = false        //true if admin login

    val isAnAdmin get() = isAdmin

    fun doLogin(username: String, password: String): LoginResult {
        val user = DataRepository.getUser(username)

        return when {
            user == null -> LoginResult.USER_NOT_FOUND
            user.password.equals(password) -> {
                DataRepository.salvaUtenteCorrente(user)  //save current user in sharedPreferences
                saveStatusIfAdmin(user)
                LoginResult.USER_LOGGED
            }
            else -> LoginResult.WRONG_PASSWORD
        }
    }

    private fun saveStatusIfAdmin(user: User) {
        isAdmin = user.admin == 1
    }

    enum class LoginResult {
        USER_NOT_FOUND,  USER_LOGGED, WRONG_PASSWORD
    }
}
