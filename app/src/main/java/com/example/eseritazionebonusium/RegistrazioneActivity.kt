package com.example.eseritazionebonusium

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.eseritazionebonusium.databinding.ActivityRegistrazioneBinding
import java.util.*

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



            if(checkMinInput(newUsername, newPassword)){
                if(verificaUguaglianzaPasswordInserite(newPassword, newConfermaPassword)){

                    var newUser : User? = verificaCredenziali(newUsername, newPassword, newCitta, newDataNascita)

                    if(newUser != null){
                        salvaCredenziali(newUser)
                        Toast.makeText(this, "Salvataggio account...", Toast.LENGTH_SHORT).show()
                        val toLoginActivity = Intent(this, LoginActivity::class.java)
                        startActivity(toLoginActivity)
                        finish()
                    }
                }
            }

        }
    }

    fun checkMinInput(newUsername: String, newPassword: String) : Boolean{
        if(newUsername.trim().isBlank()){
            binding.registrazioneUsernameEdit.setError("Name cannot be blank! ")
            return false
        }

        if(newPassword.trim().isBlank()){
            binding.registrazionePasswordEdit.setError("Password cannot be blank! ")
            return false
        }
        return true
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

    private fun verificaCredenziali(username: String, password: String,  citta: String,  dataNascita: String) : User?{
        val utente = DataRepository.getUser(username)

        if(utente.username.equals("admin")){    //can not create user with "admin" username
            return null
        }

        if (!utente.username.equals("")){   //if username already exist
            binding.registrazioneUsernameEdit.setError("Questo username e' gia utilizzato da un altro account!")
            return null;
        }else{
            val createUtente = User(username, password, citta, dataNascita)
            return createUtente
        }
    }

    /**
     * @arrayCredenziali array che puo essere o di username o di password
     * @macroTipoCredenziali indica se l array contiene serie di username o di password
     */
    private fun salvaCredenziali(newUser : User){
        DataRepository.salvaUtente(newUser)
    }

}

