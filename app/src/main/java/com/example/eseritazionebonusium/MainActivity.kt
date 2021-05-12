package com.example.eseritazionebonusium

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.eseritazionebonusium.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAdminAccount()

        var inte = Intent(this, LoginActivity::class.java)
        startActivity(inte)
        finish()
    }

    fun checkAdminAccount(){
        if(!DataRepository.adminExist()){
            Log.i("checkAdminAccount", "Admin created")
            DataRepository.createAdminAccount()
        }else{
            Log.i("checkAdminAccount", "Admin already created")
        }
    }


}



















