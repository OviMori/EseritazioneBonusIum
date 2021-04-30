package com.example.eseritazionebonusium.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelPasswordModify : ViewModel() {

    private var userList = DataRepository.getUsersList() as MutableList<User>
    private val userListLiveData = MutableLiveData<List<User>>()

    init {
        userListLiveData.value = userList   //necessario, non funzionava nuovamente l assegnamento nella dichiarazione
    }

    fun getCurrentUser(): User? {
        return DataRepository.getCurrentUser()
    }

    fun aggiornaDatiUtente(newPassword: String): Boolean {
        val currentUser = DataRepository.getCurrentUser() ?: return false

        if (!DataRepository.isAdmin(currentUser)) {  //if is not the admin account
            DataRepository.saveNewPassword(newPassword)
            return true
        } else {
            return false
        }
    }

    /**
     * True se le due stringe passate come parametro sono uguali, false altrimenti
     */
    fun passwordsFitTogether(password: String, confermaPassword: String): Boolean {
        return password.equals(confermaPassword)
    }

    /**
     * Controlla se le credenziali sono gia presenti nel sistema, se non sono presenti restituisce un utente
     */
    fun checkCredential(
            username: String,
            password: String,
            city: String,
            birth: String
    ): User? {

        if (username.equals("admin")) return null //cannot create user with "admin" username

        val user = DataRepository.getUser(username)

        return when {
            User.isValid(user) == User.UserReturnType.USER_OK -> null
            else -> {
                return User(username, password, city, birth)
            }
        }
    }
}