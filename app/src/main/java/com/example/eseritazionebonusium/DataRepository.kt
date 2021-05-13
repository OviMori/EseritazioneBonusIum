package com.example.eseritazionebonusium

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*
import kotlin.collections.ArrayList

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

    fun toStringGson(user: User): String {
        val gson = Gson()
        return  gson.toJson(UserGson(user.username, user.password, user.city, user.birth, user.eliminare, user.admin))   //convert user in gson
    }

    fun compareUsers(user1: User, user2: User): Boolean{
        return user1.equals(user2)
    }

    fun equals(obj: Objects): Boolean{
        val gson = Gson()
        val strusr1 = gson.toJson(this)
        val strusr2 = gson.toJson(obj as User)
        return strusr1.equals(strusr2)
    }

    fun fromStringGson(gsonUser: String): User?{
        val gson = Gson()
        val gu = gson.fromJson(gsonUser, UserGson::class.java)
        return User(gu.username, gu.password, gu.city, gu.birth, gu.eliminare, gu.admin)


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
            val tempUser = fromStringGson(user.value.toString())   //create new user

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

    fun saveUser(newUser: User) {
        saveCredential(newUser)
    }

    fun createAdminAccount() {
        val utenteAdmin = User("admin", "admin", "admin", "", false, 1)
        saveCredential(utenteAdmin)
    }

    fun adminExist(): Boolean {
        return sharPrefUsers.getString("admin", null) != null
    }

    fun getCurrentUser(): User? {
        return sharPrefMyUser.getString(UTENTE_CORRENTE_KEY, null)?.let { fromStringGson(it) }
    }

    fun saveCurrentUser(currentUser: User) {
        Log.i("Current user saved: ", currentUser.toString())
        val gsonString = toStringGson(currentUser)
        sharPrefMyUser.edit().putString(UTENTE_CORRENTE_KEY, gsonString).apply()
    }

    fun getUser(username: String): User? {
        return sharPrefUsers.getString(username, null)
            .let { it?.let { it1 -> fromStringGson(it1) } }
    }

    private fun saveCredential(newUser: User) {
        Log.i("InfoUserRegistered", newUser.toString())
        if (User.isValid(newUser) == User.UserReturnType.USER_OK) {
            val gsonString = toStringGson(newUser)
            Log.i("saveCredential", gsonString)
            sharPrefUsers.edit().putString(newUser.username, gsonString).apply()
        }
    }


}