package com.example.eseritazionebonusium.registration

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelRegistration : ViewModel() {

    fun registrationProceedings(
            username: String,
            password: String,
            confirmPassword: String,
            city: String,
            birth: String
    ): RegistrationReturnValue {
        when (checkMinInput(username, password)) {
            RegistrationReturnValue.INVALID_USERNAME -> return RegistrationReturnValue.INVALID_USERNAME
            RegistrationReturnValue.INVALID_PASSWORD -> return RegistrationReturnValue.INVALID_PASSWORD
            else ->
                if (passwordsMatch(password, confirmPassword) == RegistrationReturnValue.PASSWORDS_OK) {
                    if (checkUsername(username) == RegistrationReturnValue.USERNAME_OK) {
                        saveCredentials(username, password, city, birth)


                        return RegistrationReturnValue.USER_DATA_SAVED
                    } else {
                        return RegistrationReturnValue.USER_ALREADY_EXIST
                    }
                } else {
                    return RegistrationReturnValue.INVALID_PASSWORD_MATCH
                }
        }
    }

    /**
     * True se le due stringe passate come parametro sono uguali, false altrimenti
     */
    fun passwordsMatch(password: String, confermaPassword: String): RegistrationReturnValue {
        return if (password.equals(confermaPassword)) RegistrationReturnValue.PASSWORDS_OK else RegistrationReturnValue.INVALID_PASSWORD
    }

    /**
     * Controlla se le credenziali sono gia presenti nel sistema, se non sono presenti restituisce un utente
     */
    fun checkUsername(
            username: String,
    ): RegistrationReturnValue {

        if (username.toLowerCase().contains("admin")) return RegistrationReturnValue.INVALID_USERNAME //cannot use 'admin' username
        val utente = DataRepository.getUser(username)
        return if (User.isValid(utente) == User.UserReturnType.USER_OK) RegistrationReturnValue.USER_ALREADY_EXIST else RegistrationReturnValue.USERNAME_OK
    }

    fun checkMinInput(newUsername: String, newPassword: String): RegistrationReturnValue {
        if (newUsername.trim().isBlank()) {
            return RegistrationReturnValue.INVALID_USERNAME
        }
        if (newPassword.trim().isBlank()) {
            return RegistrationReturnValue.INVALID_PASSWORD
        }
        return RegistrationReturnValue.CREDENTIAL_OK
    }

    /**
     * @arrayCredenziali array che puo essere o di username o di password
     * @macroTipoCredenziali indica se l array contiene serie di username o di password
     */
    fun saveCredentials(username: String,
                        password: String,
                        citta: String,
                        dataNascita: String) {

        if (checkUsername(username) == RegistrationReturnValue.USERNAME_OK) {
            val newUser = User(username, password, citta, dataNascita)
            DataRepository.saveUser(newUser)
        }
    }

    enum class RegistrationReturnValue {
        INVALID_USERNAME,
        INVALID_PASSWORD,
        INVALID_PASSWORD_MATCH,
        USER_ALREADY_EXIST,
        CREDENTIAL_OK,
        PASSWORDS_OK,
        USERNAME_OK,
        USER_DATA_SAVED
    }
}