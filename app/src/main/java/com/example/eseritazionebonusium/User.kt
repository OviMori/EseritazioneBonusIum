package com.example.eseritazionebonusium

import android.icu.text.Edits
import com.google.gson.Gson
import com.google.gson.GsonBuilder

data class User(
        var username: String = "",
        var password: String = "",
        var citta: String = "",
        var dataNascita: String = "",
        var eliminare: Boolean = false,
        var admin: Int = 0
) : HashSet<String>() {


    val isAdmin: Boolean get() = admin == 1

    override fun toString(): String {
        return toStringGson(this)
//        return "" + this.username + "*" + this.password + "*" + this.citta + "*" + this.dataNascita + "*" + this.eliminare + "*" + this.admin
    }

    companion object {
        val USER_SERIALIZATION_ARRAY_DIM = 6

        fun fromString(str: String?): User? {

            if (str != null && isValidString(str) == UserReturnType.USER_STRING_OK) {
                return fromStringGson(str)
//                val strArrUser = str.split("*")
//                /*Controls*/
//                var user = User()
//
//                user.username = strArrUser[0]
//                user.password = strArrUser[1]
//                user.citta = strArrUser[2]
//                user.dataNascita = strArrUser[3]
//
//                if (strArrUser[4] == "true") {
//                    user.eliminare = true
//                }
//
//                if (strArrUser[5] == "1") {
//                    user.admin = 1
//                } else {
//                    user.admin = 0
//                }
//                return user
            }
            return null
        }

        fun isValid(user: User?): UserReturnType {
            return when {
                user == null -> UserReturnType.INVALID_USER
                user.username.isBlank() -> UserReturnType.INVALID_USERNAME
                user.password.isBlank() -> UserReturnType.INVALID_PASSWORD
                else -> UserReturnType.USER_OK
            }
        }

        fun isValidString(strUser: String?): UserReturnType {
            //Posso usare il costrutto when per i vari casi

            if (strUser == null) return UserReturnType.INVALID_STRING

            val strArrUser = strUser.split("*")
            if (strArrUser.size != USER_SERIALIZATION_ARRAY_DIM) return UserReturnType.INVALID_STRING

            val tempUser = User(strArrUser[0], strArrUser[1])
            return if (isValid(tempUser) == UserReturnType.USER_OK) UserReturnType.USER_STRING_OK else UserReturnType.INVALID_STRING
        }

        fun toStringGson(user: User): String {
            val gson: Gson = Gson()
            return  gson.toJson(user)   //converto user in gson
        }

        fun fromStringGson(gsonUser: String): User?{
            val gson = Gson()
            return gson.fromJson(gsonUser, User::class.java)

        }
    }


    enum class UserReturnType {
        INVALID_STRING,
        INVALID_USERNAME,
        INVALID_PASSWORD,
        INVALID_USER,
        USER_STRING_OK,
        USERNAME_OK,
        PASSWORD_OK,
        USER_OK

    }

}