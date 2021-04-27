package com.example.eseritazionebonusium.vm

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelRegistration : ViewModel(){

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

    /**
     * @arrayCredenziali array che puo essere o di username o di password
     * @macroTipoCredenziali indica se l array contiene serie di username o di password
     */
    fun salvaCredenziali(newUser : User){
        DataRepository.salvaUtente(newUser)
    }
}