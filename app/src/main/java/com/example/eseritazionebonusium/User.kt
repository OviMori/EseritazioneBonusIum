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
        val USER_SERIALIZATION_ARRAY_DIM = 6

        fun fromString(str : String?) : User?{
            //Posso usare il costrutto when per i vari casi

            if(str == null ) return null

            var strArrUser = str.split("*")
            if(strArrUser.size != USER_SERIALIZATION_ARRAY_DIM ) return null

            if()


            /*Controls*/
            var user = User()
//            var strArrUtente = str.split("*")
            user.username = strArrUser[0]
            user.password = strArrUser[1]
            user.citta = strArrUser[2]
            user.dataNascita = strArrUser[3]

            if(strArrUser[4] == "true"){
                user.eliminare = true
            }

            if(strArrUser[5] == "1"){
                user.admin = 1
            }else{
                user.admin = 0
            }
            return user
        }
    }
}