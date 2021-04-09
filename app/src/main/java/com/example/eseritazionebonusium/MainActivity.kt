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
        if(!DataRepository.adminExist()){   //if admin does not exist
            DataRepository.createAdminAccount()
        }
    }
}



















