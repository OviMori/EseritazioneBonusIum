package com.example.eseritazionebonusium

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.eseritazionebonusium.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.registrazioneButton.setOnClickListener{
            openRegistrazioneActivity()
        }

        binding.accediButton.setOnClickListener{
            var usernameInserito = binding.usernameEdit.text.toString()
            var passwordInserita = binding.passwordEdit.text.toString()
            if(verificaCredenziali(usernameInserito, passwordInserita)){
                openHomeActivity()
            }else{
                binding.usernameEdit.setError("Le credenziali non sono corrette!")
                binding.passwordEdit.setError("Le credenziali non sono corrette!")
            }
        }

    }

    private fun verificaCredenziali(username: String, password: String) : Boolean{
        var loginPref : SharedPreferences = getSharedPreferences(R.string.INFO_UTENTI.toString(), MODE_PRIVATE)
        val exist: String = loginPref.getString(username, "") as String

        if (!exist.equals("")){

            val tmpUtente = Utente()
            tmpUtente.creaNuovoUtenteDaStringa(exist)    //creiamo un nuovo utente e lo popoliamo con le info salvate come sharedPreferences

            if(tmpUtente.password.equals(password)){
                salvaUtenteCorrente(tmpUtente)
                return true;
            }
        }else{
            //binding.usernameEdit.setError("Questo account non esiste!")
            return false
        }
        return false
    }

    private fun salvaUtenteCorrente(utenteCorrente : Utente){
        var logInPref : SharedPreferences = getSharedPreferences(R.string.INFO_UTENTI.toString(), Context.MODE_PRIVATE)
        var edit : SharedPreferences.Editor = logInPref.edit()

        edit.putString(R.string.UTENTE_CORRENTE.toString(), utenteCorrente.toString()).apply()
    }

    private fun openHomeActivity(){
        val toHomeIntent = Intent(this, HomeActivity::class.java)
        startActivity(toHomeIntent)
        finish()
    }

    private fun openRegistrazioneActivity(){
        val registrazioneIntent = Intent(this, RegistrazioneActivity::class.java)
        startActivity(registrazioneIntent)
        finish()
    }
}