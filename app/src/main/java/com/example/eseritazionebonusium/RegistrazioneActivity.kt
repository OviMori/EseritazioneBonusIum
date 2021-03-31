package com.example.eseritazionebonusium

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.eseritazionebonusium.databinding.ActivityRegistrazioneBinding
import java.util.*
import kotlin.collections.HashSet

class RegistrazioneActivity : AppCompatActivity() {


    private val CREDENZIALI_SALVATE_USERNAME : String = "SET_CREDENZIALI_USERNAME"
    private val CREDENZIALI_SALVATE_PASSWORD : String = "SET_CREDENZIALI_PASSWORD"

    private lateinit var binding : ActivityRegistrazioneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrazione)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registrazione)


        binding.registrazioneDataNascita.setOnClickListener {
            showDataPicker()
        }



        binding.registratiButton.setOnClickListener{
            var newUsername : String = binding.registrazioneUsernameEdit.text.toString()
            var newPassword : String = binding.registrazionePasswordEdit.text.toString()
            var newConfermaPassword : String = binding.registrazioneConfermaPasswordEdit.text.toString()
            var newCitta : String = binding.registrazioneCitta.text.toString()
            var newDataNascita : String = binding.registrazioneDataNascita.text.toString()



            if(verificaUguaglianzaPasswordInserite(newPassword, newConfermaPassword)){
                var newUtente : Utente? = verificaCredenziali(newUsername, newPassword, newCitta, newDataNascita)
                if(newUtente != null){
                    salvaCredenziali(newUtente)
                    Toast.makeText(this, "Salvataggio account...", Toast.LENGTH_SHORT).show()
                    val toLoginActivity = Intent(this, LoginActivity::class.java)
                    startActivity(toLoginActivity)
                    finish()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDataPicker(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in TextView
            binding.registrazioneDataNascita.setText("" + dayOfMonth + ", " + month + ", " + year)
        }, year, month, day)
        dpd.show()
    }

    private fun verificaUguaglianzaPasswordInserite(password : String, confermaPassword : String) : Boolean{
        if(password.equals(confermaPassword)){
            return true
        }else{
            binding.registrazionePasswordEdit.setError("Le password inserite non corrispondono!")
            return false
        }
    }

    private fun verificaCredenziali(username: String, password: String,  citta: String,  dataNascita: String) : Utente?{
        var loginPref : SharedPreferences = getSharedPreferences(R.string.INFO_UTENTI.toString(), MODE_PRIVATE)
        val exist: String = loginPref.getString(username, "") as String


        if (!exist.equals("")){
            binding.registrazioneUsernameEdit.setError("Questo username e' gia utilizzato da un altro account!")
            return null;
        }else{
            val createUtente = Utente(username, password, citta, dataNascita)
            return createUtente
        }
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

    }

}

