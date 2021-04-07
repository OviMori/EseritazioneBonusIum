package com.example.eseritazionebonusium

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.example.eseritazionebonusium.databinding.ActivityHomeBinding
import com.example.eseritazionebonusium.databinding.ActivityModificaPasswordBinding
import kotlin.math.log

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)


        val intent = getIntent()
        val isAnAdmin : Int = intent.getIntExtra("ISANADMIN", 0)

        Log.i("Value of ISANADMIN**************", ""+isAnAdmin)

        if(isAnAdmin == 1){
            //mostra layout admin
            binding.adminIconLayout.visibility = View.VISIBLE
            binding.gestisciUtentiButton.visibility = View.VISIBLE
        }else{
            //nascondi layout admin
            binding.adminIconLayout.visibility = View.GONE
            binding.gestisciUtentiButton.visibility = View.GONE
        }
        setDatiUtenteCorrente()

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
        val sharedPref = getSharedPreferences(R.string.INFO_UTENTI.toString(), MODE_PRIVATE)
        val datiUtenteCorrenteString : String = sharedPref.getString(R.string.UTENTE_CORRENTE.toString(), "").toString()

        val utenteCorrente = Utente()
        utenteCorrente.creaNuovoUtenteDaStringa(datiUtenteCorrenteString)

        binding.homeUsername.setText(utenteCorrente.username)
        binding.homePassword.setText(utenteCorrente.password)
        binding.homeCitta.setText(utenteCorrente.citta)
        binding.homeDataNascita.setText(utenteCorrente.dataNascita)

    }
}