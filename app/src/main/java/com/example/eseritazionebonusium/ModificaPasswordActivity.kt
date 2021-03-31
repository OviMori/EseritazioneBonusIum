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

    private lateinit var utenteCorrenteGlobale : Utente

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
                aggiornaDatiUtente(utenteCorrenteGlobale, newPassword)
                binding.modificaPasswordPasswordCorrente.setText(newPassword)
            }
        }

    }

    fun aggiornaDatiUtente(utenteCorrenteGlobale : Utente, newPassword : String){
        utenteCorrenteGlobale.password = newPassword
        salvaCredenziali(utenteCorrenteGlobale)
    }

    /**
     * @arrayCredenziali array che puo essere o di username o di password
     * @macroTipoCredenziali indica se l array contiene serie di username o di password
     */
    @SuppressLint("CommitPrefEdits")
    private fun salvaCredenziali(newUtente : Utente){

        var logInPref : SharedPreferences = getSharedPreferences(R.string.INFO_UTENTI.toString(), Context.MODE_PRIVATE)
        var edit : SharedPreferences.Editor = logInPref.edit()

        edit.putString(newUtente.username, newUtente.toString()).apply()
        edit.putString(R.string.UTENTE_CORRENTE.toString(), newUtente.toString()).apply()


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
        val sharedPref = getSharedPreferences(R.string.INFO_UTENTI.toString(), MODE_PRIVATE)
        val datiUtenteCorrenteString : String = sharedPref.getString(R.string.UTENTE_CORRENTE.toString(), "").toString()

        val utenteCorrente = Utente()
        utenteCorrente.creaNuovoUtenteDaStringa(datiUtenteCorrenteString)
        utenteCorrenteGlobale = utenteCorrente

        binding.modificaPasswordUsernameCorrente.setText(utenteCorrente.username)
        binding.modificaPasswordPasswordCorrente.setText(utenteCorrente.password)
    }
}