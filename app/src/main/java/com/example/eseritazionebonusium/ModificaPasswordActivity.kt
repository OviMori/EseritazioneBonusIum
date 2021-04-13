package com.example.eseritazionebonusium

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
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
                if(checkMinInput(newPassword)){
                    aggiornaDatiUtente(newPassword)
                    setDatiUtenteCorrente()
                    Toast.makeText(this, " Password updated. ", Toast.LENGTH_SHORT).show()
                }

            }
        }

        binding.passwordEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.passwordEdit.setError(null)
                binding.textInputLayoutModificaPassword.isEndIconVisible = true
            }

            @SuppressLint("WrongConstant")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.passwordConfermaEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            @SuppressLint("WrongConstant")
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.passwordConfermaEdit.setError(null)
                binding.textInputLayoutConfermaModificaPassword.isEndIconVisible = true
            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    fun checkMinInput(newPassword: String) : Boolean{
        if(newPassword.trim().isBlank()){
            binding.textInputLayoutModificaPassword.isEndIconVisible = false
            binding.passwordEdit.setError("Password cannot be blank! ")
            return false
        }
        return true
    }

    fun aggiornaDatiUtente( newPassword : String){
        val currentUser = DataRepository.getCurrentUser()

        if(!currentUser.username.equals("admin")){  //if is not the admin account
            DataRepository.salvaCambioPassword(newPassword)
        }else{
            Toast.makeText(this, "Cannot modify admin credential", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("WrongConstant")
    private fun verificaUguaglianzaPasswordInserite(password : String, confermaPassword : String) : Boolean{
        if(password.equals(confermaPassword)){
            return true
        }else{
            binding.textInputLayoutModificaPassword.isEndIconVisible = false
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