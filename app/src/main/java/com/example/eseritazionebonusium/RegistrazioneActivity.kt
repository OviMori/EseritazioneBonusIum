package com.example.eseritazionebonusium

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.databinding.ActivityRegistrazioneBinding
import com.example.eseritazionebonusium.vm.Injector
import com.example.eseritazionebonusium.vm.UserViewModel
import java.util.*

class RegistrazioneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrazioneBinding
    private lateinit var model : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrazione)

        val factory = Injector.provideMyUserViewModelFactory()
        model = ViewModelProvider(this, factory).get(UserViewModel::class.java)

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
                if(model.verificaUguaglianzaPasswordInserite(newPassword, newConfermaPassword)){

                    var newUser : User? = model.verificaCredenziali(newUsername, newPassword, newCitta, newDataNascita)

                    if(newUser != null){
                        model.salvaCredenziali(newUser)
                        Toast.makeText(this, "Salvataggio account...", Toast.LENGTH_SHORT).show()
                        val toLoginActivity = Intent(this, LoginActivity::class.java)
                        startActivity(toLoginActivity)
                        finish()
                    }else {
                        binding.registrazioneUsernameEdit.setError("Questo username e' gia utilizzato da un altro account!")
                    }
                }else{
                    binding.registrazionePasswordEdit.setError("Le password inserite non corrispondono!")
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

}

