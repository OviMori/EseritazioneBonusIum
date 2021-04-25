package com.example.eseritazionebonusium

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.databinding.ActivityHomeBinding
import com.example.eseritazionebonusium.gestioneutenti.GestioneUtentiActivity
import com.example.eseritazionebonusium.gestioneutenti.UserViewHolder
import com.example.eseritazionebonusium.vm.Injector
import com.example.eseritazionebonusium.vm.UserViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel : UserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val factory = Injector.provideMyUserViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
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