package com.example.eseritazionebonusium

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verifyAdminAccount()
        var inte = Intent(this, LoginActivity::class.java)
        startActivity(inte)
        finish()
    }

    private fun verifyAdminAccount(){
        if(!adminExist()){
            createAdminAccount()
        }
    }

    private fun adminExist() : Boolean{
        var sharedPref : SharedPreferences = getSharedPreferences(R.string.INFO_UTENTI.toString(), MODE_PRIVATE)
        if(sharedPref.getString("admin", "") == ""){
            return false
        }
        return true
    }

    private fun createAdminAccount(){
        var sharedPref : SharedPreferences = getSharedPreferences(R.string.INFO_UTENTI.toString(), MODE_PRIVATE)
        var editSharedPref : SharedPreferences.Editor = sharedPref.edit()

        var utenteAdmin = Utente("admin", "admin", "admin", "", false, 1)

        editSharedPref.putString("admin", utenteAdmin.toString()).apply()

    }
}



















