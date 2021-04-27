package com.example.eseritazionebonusium.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelPasswordModify : ViewModel() {

    private var userList = DataRepository.getUsersList() as MutableList<User>
    private val userListLiveData = MutableLiveData<List<User>>(userList)
    private var isAdmin = false        //true if admin login


    fun checkUserList(): MutableLiveData<List<User>> {
        return userListLiveData
    }

    fun getUserList(): List<User> {
        return userList
    }

    val isAnAdmin get() = isAdmin

    fun getCurrentUser(): User {
        return DataRepository.getCurrentUser()
    }

    fun aggiornaDatiUtente(newPassword: String): Boolean {
        val currentUser = DataRepository.getCurrentUser()

        if (!currentUser.username.equals("admin")) {  //if is not the admin account
            DataRepository.salvaCambioPassword(newPassword)
            return true
        } else {
            return false
        }
    }

    /**
     * True se le due stringe passate come parametro sono uguali, false altrimenti
     */
    fun verificaUguaglianzaPasswordInserite(password: String, confermaPassword: String): Boolean {
        return password.equals(confermaPassword)
    }

    /**
     * Controlla se le credenziali sono gia presenti nel sistema, se non sono presenti restituisce un utente
     */
    fun verificaCredenziali(
            username: String,
            password: String,
            citta: String,
            dataNascita: String
    ): User? {
        val utente = DataRepository.getUser(username)

        if (utente.username.equals("admin")) {    //can not create user with "admin" username
            return null
        }

        if (!utente.username.equals("")) {   //if username already exist
            return null;
        } else {
            val createUtente = User(username, password, citta, dataNascita)
            return createUtente
        }
    }

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

    /**
     * @arrayCredenziali array che puo essere o di username o di password
     * @macroTipoCredenziali indica se l array contiene serie di username o di password
     */
    fun salvaCredenziali(newUser: User) {
        DataRepository.salvaUtente(newUser)
    }
}