package com.example.eseritazionebonusium

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object DataRepository {
    private const val MY_SHARED_PREF_UTENTI = "INFO_UTENTI"
    private const val MY_SHARED_PREF_UTENTE_CORRENTE = "UTENTE_CORRENTE"
    private const val UTENTE_CORRENTE_KEY = "key_current_user"


    private lateinit var sharPrefUsers : SharedPreferences
    private lateinit var sharPrefMyUser : SharedPreferences

    fun init(context : Context){
        sharPrefUsers = context.getSharedPreferences(MY_SHARED_PREF_UTENTI, Context.MODE_PRIVATE)
        sharPrefMyUser = context.getSharedPreferences(MY_SHARED_PREF_UTENTE_CORRENTE, Context.MODE_PRIVATE)
        verifyAdminAccount()
    }
    private fun verifyAdminAccount(){
        if(!adminExist()){   //if admin does not exist
            createAdminAccount()
        }
    }

    fun removeAllAccounts(){
        sharPrefUsers.edit().clear().apply()
    }

    fun setAdmin(user: User): Boolean{
        if(userExist(user.username)){
            user.admin = 1
            salvaUtente(user)
            return true
        }else{
            return false
        }
    }

    fun dropUser(user: User) : Boolean{
        if(sharPrefUsers.contains(user.username)){
            Log.i("Drop User", user.toString())
            sharPrefUsers.edit().remove(user.username).apply()

            for(tempUser in sharPrefUsers.all.toList()){
                Log.i("For test befor drop", tempUser.second.toString())
            }
            return true
        }
        return false
    }

    fun getUsersList() : List<User>{
        var userList: ArrayList<User> = ArrayList()
        val userListFromPreferences: Map<String, *> = sharPrefUsers.all


        for(user in userListFromPreferences){
            val tempUser = User()
            tempUser.creaNuovoUtenteDaStringa(user.value.toString())   //create new user
            userList.add(tempUser)
        }

        for(tempUser in userList){
            Log.i("For test after drop", tempUser.toString())
        }

        return userList
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
        val tempAdmin = sharPrefUsers.getString(username, "")
        if(tempAdmin.equals("")){   //if admin does not exist
            return false
        }
        return true
    }

    fun salvaUtenteCorrente(currentUser : User){
        Log.i("salvataggio Utente  corrente ", currentUser.toString())
        sharPrefMyUser.edit().putString(UTENTE_CORRENTE_KEY, currentUser.toString()).apply()
    }

    fun salvaUtente(newUser : User){
        salvaCredenziali(newUser)
    }

    fun getCurrentUser() : User{
        val strCurrentUser = sharPrefMyUser.getString(UTENTE_CORRENTE_KEY, "") as String
        val currentUser = User()
        Log.i("recupero Utente  corrente ", "" + strCurrentUser)

        if (!strCurrentUser.equals("")) {
            currentUser.creaNuovoUtenteDaStringa(strCurrentUser)
        }
        return currentUser
    }

    fun getUser(username: String): User? {
        val utenteInString = sharPrefUsers.getString(username, null)
        return User.fromString(utenteInString)
    }

    fun createAdminAccount() {
        val utenteAdmin = User("admin", "admin", "admin", "", false, 1)
        salvaCredenziali(utenteAdmin)
    }

    fun adminExist() : Boolean{
        val tempAdmin = sharPrefUsers.getString("admin", "")
        if(tempAdmin.equals("")){   //if admin does not exist
            return false
        }
        return true
    }

    private fun salvaCredenziali(newUser : User){
        Log.i("InfoUtenteRegistrato", newUser.toString())
        sharPrefUsers.edit().putString(newUser.username, newUser.toString()).apply()
    }


}