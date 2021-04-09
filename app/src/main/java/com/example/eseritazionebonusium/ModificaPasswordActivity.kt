package com.example.eseritazionebonusium

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.eseritazionebonusium.databinding.ActivityModificaPasswordBinding

class ModificaPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityModificaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifica_password)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_modifica_password)

        setDatiUtenteCorrente()

        binding.tornaHomeButton.setOnClickListener{
            val homeIntent : Intent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

        binding.aggiornaPasswordButton.setOnClickListener{
            val newPassword : String = binding.passwordEdit.text.toString()
            val newPasswordConferma : String = binding.passwordConfermaEdit.text.toString()

            if(verificaUguaglianzaPasswordInserite(newPassword, newPasswordConferma)){
                aggiornaDatiUtente(newPassword)
                setDatiUtenteCorrente()
            }
        }

    }

    fun aggiornaDatiUtente( newPassword : String){
        DataRepository.salvaCambioPassword(newPassword)
    }

    private fun verificaUguaglianzaPasswordInserite(password : String, confermaPassword : String) : Boolean{
        if(password.equals(confermaPassword)){
            return true
        }else{
            binding.passwordEdit.setError("Le password inserite non corrispondono!")
            return false
        }
    }

    private fun setDatiUtenteCorrente(){
        val utenteCorrente = DataRepository.getCurrentUser()

        binding.modificaPasswordUsernameCorrente.setText(utenteCorrente.username)
        binding.modificaPasswordPasswordCorrente.setText(utenteCorrente.password)
    }
}