package com.example.eseritazionebonusium

import android.icu.text.Edits

data class User(
    var username: String = "",
    var password : String = "",
    var  citta : String = "",
    var  dataNascita : String = "",
    var eliminare : Boolean = false,
    var admin : Int = 0
) : HashSet<String>() {

    val isAdmin: Boolean get() = admin == 1

    override fun toString(): String {
        return ""+this.username+"*"+this.password+"*"+this.citta+"*"+this.dataNascita+"*"+this.eliminare+"*"+this.admin
    }

    companion object{
        fun fromString(str : String?) : User?{
            if(str == null ) return null

            /*Controls*/
            var user = User()
            var strArrUtente = str.split("*")
            user.username = strArrUtente[0]
            user.password = strArrUtente[1]
            user.citta = strArrUtente[2]
            user.dataNascita = strArrUtente[3]

            if(strArrUtente[4] == "true"){
                user.eliminare = true
            }

            if(strArrUtente[5] == "1"){
                user.admin = 1
            }else{
                user.admin = 0
            }
            return user
        }
    }
}