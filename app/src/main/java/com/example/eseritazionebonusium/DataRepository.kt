package com.example.eseritazionebonusium

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object DataRepository {
    private const val MY_SHARED_PREF_UTENTI = "INFO_UTENTI"
    private const val MY_SHARED_PREF_UTENTE_CORRENTE = "UTENTE_CORRENTE"
    private const val UTENTE_CORRENTE_KEY = "key_current_user"


    private lateinit var sharPrefUsers: SharedPreferences
    private lateinit var sharPrefMyUser: SharedPreferences

    fun init(context: Context) {
        sharPrefUsers = context.getSharedPreferences(MY_SHARED_PREF_UTENTI, Context.MODE_PRIVATE)
        sharPrefMyUser = context.getSharedPreferences(MY_SHARED_PREF_UTENTE_CORRENTE, Context.MODE_PRIVATE)
        verifyAdminAccount()
    }

    private fun verifyAdminAccount() {
        if (adminExist() == false) {   //if admin does not exist
            createAdminAccount()
        }
    }

    fun setAdmin(user: User): Boolean {
        if (userExist(user)) {
            user.admin = 1
            saveUser(user)
            return true
        } else {
            return false
        }
    }

    fun isAdmin(user: User): Boolean{
        return if(user.isAdmin) true else false
    }

    fun dropUser(user: User): Boolean {
        if (sharPrefUsers.contains(user.username)) {
            Log.i("Drop User", user.toString())
            sharPrefUsers.edit().remove(user.username).apply()
            return true
        }
        return false
    }

    fun getUsersList(): List<User> {
        var userList: ArrayList<User> = ArrayList()

        for (user in sharPrefUsers.all) {
            val tempUser = User.fromString(user.value.toString())   //create new user

            if (tempUser != null && User.isValid(tempUser) == User.UserReturnType.USER_OK)
                userList.add(tempUser)
        }
        return userList
    }

    fun saveNewPassword(newPassword: String) {
        val currentUser = getCurrentUser()  //necessary for 'null' controll
        if(currentUser != null && User.isValid(currentUser) == User.UserReturnType.USER_OK){
            currentUser.password = newPassword
            //change either current user credential and credential in the list of all users
            saveUser(currentUser)
        }
    }

    fun userExist(user: User): Boolean {
        return sharPrefUsers.getString(user.username, null) != null
    }

    fun saveCurrentUser(currentUser: User) {
        Log.i("Current user saved: ", currentUser.toString())
        sharPrefMyUser.edit().putString(UTENTE_CORRENTE_KEY, currentUser.toString()).apply()
    }

    fun saveUser(newUser: User) {
        saveCredential(newUser)
    }

    fun getCurrentUser(): User? {
        return User.fromString(sharPrefMyUser.getString(UTENTE_CORRENTE_KEY, null))
    }

    fun getUser(username: String): User? {
        val utenteInString = sharPrefUsers.getString(username, null)
        return User.fromString(utenteInString)
    }

    fun createAdminAccount() {
        val utenteAdmin = User("admin", "admin", "admin", "", false, 1)
        saveCredential(utenteAdmin)
    }

    fun adminExist(): Boolean {
        return sharPrefUsers.getString("admin", null) != null
    }

    private fun saveCredential(newUser: User) {
        Log.i("InfoUserRegistered", newUser.toString())
        if(User.isValid(newUser) == User.UserReturnType.USER_OK){
            sharPrefUsers.edit().putString(newUser.username, newUser.toString()).apply()
        }
    }


}