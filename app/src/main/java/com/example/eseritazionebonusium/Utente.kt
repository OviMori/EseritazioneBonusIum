package com.example.eseritazionebonusium

import android.icu.text.Edits

data class Utente(
    var username: String = "",
    var password : String = "",
    var  citta : String = "",
    var  dataNascita : String = "",
    var eliminare : Boolean = false,
    var admin : Int = 0
) : HashSet<String>() {

    fun  creaNuovoUtenteDaStringa(str : String){
        var strArrUtente = str.split("*")
        this.username = strArrUtente[0]
        this.password = strArrUtente[1]
        this.citta = strArrUtente[2]
        this.dataNascita = strArrUtente[3]

        if(strArrUtente[4] == "true"){
            this.eliminare = true
        }

        if(strArrUtente[5] == "1"){
            this.admin = 1
        }
    }

    override fun toString(): String {
        return ""+this.username+"*"+this.password+"*"+this.citta+"*"+this.dataNascita+"*"+this.eliminare+"*"+this.admin
    }
}