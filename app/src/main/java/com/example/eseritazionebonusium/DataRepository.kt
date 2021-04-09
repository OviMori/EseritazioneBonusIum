package com.example.eseritazionebonusium

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

object DataRepository {
    private const val MY_SHARED_PREF_UTENTI = "INFO_UTENTI"
    private const val MY_SHARED_PREF_UTENTE_CORRENTE = "UTENTE_CORRENTE"
    private const val UTENTE_CORRENTE_KEY = "key_current_user"


    private lateinit var sharPrefUsers : SharedPreferences
    private lateinit var sharPrefMyUser : SharedPreferences

    fun init(context : Context){
        sharPrefUsers = context.getSharedPreferences(MY_SHARED_PREF_UTENTI, Context.MODE_PRIVATE)
        sharPrefMyUser = context.getSharedPreferences(MY_SHARED_PREF_UTENTE_CORRENTE, Context.MODE_PRIVATE)
    }

    fun getUsersList() : Map<String, String>{
        return sharPrefUsers.all as Map<String, String>
    }

    fun getSharedPref() : SharedPreferences{
        return sharPrefUsers
    }

    fun salvaCambioPassword(newPassword : String){
        val userWithNewPassword = getCurrentUser()
        userWithNewPassword.password = newPassword

        //change either current user credential and credential in the list of all users
        salvaUtenteCorrente(userWithNewPassword)
        salvaUtente(userWithNewPassword)
    }

    fun userExist(username : String) : Boolean{
        var tempAdmin = sharPrefUsers.getString(username, "")
        if(tempAdmin.equals("")){   //if admin does not exist
            return false
        }
        return true
    }

    fun salvaUtenteCorrente(currentUser : Utente){
        Log.i("salvataggio Utente  corrente ", currentUser.toString())
        sharPrefMyUser.edit().putString(UTENTE_CORRENTE_KEY, currentUser.toString()).apply()
    }

    fun salvaUtente(newUtente : Utente){
        salvaCredenziali(newUtente)
    }

    fun getCurrentUser() : Utente{
        val strCurrentUser = sharPrefMyUser.getString(UTENTE_CORRENTE_KEY, "") as String
        val currentUser = Utente()
        Log.i("recupero Utente  corrente ", ""+strCurrentUser)

        if(!strCurrentUser.equals("")){
            currentUser.creaNuovoUtenteDaStringa(strCurrentUser)
        }
        return currentUser
    }

    fun getUser(username : String) : Utente{
        val userGeneratedFromString = Utente()
        val utenteInString : String = sharPrefUsers.getString(username, "") as String

        Log.i("Username passed ", username)
        Log.i("Utente generato ", utenteInString)

        if(!utenteInString.equals("")){ //if user exist
            userGeneratedFromString.creaNuovoUtenteDaStringa(utenteInString)
        }
        return userGeneratedFromString
    }

    fun createAdminAccount(){
        var utenteAdmin = Utente("admin", "admin", "admin", "", false, 1)
        salvaCredenziali(utenteAdmin)
    }

    fun adminExist() : Boolean{
        var tempAdmin = sharPrefUsers.getString("admin", "")
        if(tempAdmin.equals("")){   //if admin does not exist
            return false
        }
        return true
    }

    private fun salvaCredenziali(newUtente : Utente){
        Log.i("InfoUtenteRegistrato", newUtente.toString())
        sharPrefUsers.edit().putString(newUtente.username, newUtente.toString()).apply()
    }


}