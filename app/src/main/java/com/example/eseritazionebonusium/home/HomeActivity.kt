package com.example.eseritazionebonusium.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.changepassword.ModificaPasswordActivity
import com.example.eseritazionebonusium.databinding.ActivityHomeBinding
import com.example.eseritazionebonusium.usermanager.GestioneUtentiActivity
import com.example.eseritazionebonusium.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: UserViewModelHome

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModelHome::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)



        if(viewModel.isAnAdmin){
            //mostra layout admin
            binding.adminIconLayout.visibility = View.VISIBLE
            binding.gestisciUtentiButton.visibility = View.VISIBLE
        }else{
            //nascondi layout admin
            binding.adminIconLayout.visibility = View.GONE
            binding.gestisciUtentiButton.visibility = View.GONE
        }

        setDatiUtenteCorrente()

        binding.homeMainNome.text = "Benvenuto "+ DataRepository.getCurrentUser().username

        binding.gestisciUtentiButton.setOnClickListener{
            val logoutIntent : Intent  = Intent(this, GestioneUtentiActivity::class.java)
            startActivity(logoutIntent)
            finish()
        }


        binding.modificaPasswordButton.setOnClickListener{
            val logoutIntent : Intent  = Intent(this, ModificaPasswordActivity::class.java)
            startActivity(logoutIntent)
            finish()
        }

        binding.logoutButton.setOnClickListener{
            val logoutIntent : Intent  = Intent(this, LoginActivity::class.java)
            startActivity(logoutIntent)
            finish()
        }

    }

    private fun setDatiUtenteCorrente(){
        val currentUser = DataRepository.getCurrentUser()

        Log.i("UtenteCorrente:::::::::::::::::::", ""+currentUser.toString())

        binding.homeUsername.setText(currentUser.username)
        binding.homePassword.setText(currentUser.password)
        binding.homeCitta.setText(currentUser.citta)
        binding.homeDataNascita.setText(currentUser.dataNascita)
    }
}