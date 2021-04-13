package com.example.eseritazionebonusium

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.eseritazionebonusium.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private var isAnAdmin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.registrazioneButton.setOnClickListener{
            openRegistrazioneActivity()
        }

        binding.passwordEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.textInputLayout.isEndIconVisible = true
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.passwordEdit.setError(null)
            }
        })

        binding.accediButton.setOnClickListener{
            var usernameInserito = binding.usernameEdit.text.toString()
            var passwordInserita = binding.passwordEdit.text.toString()
            if(verificaCredenziali(usernameInserito, passwordInserita)){
                openHomeActivity()
            }else{
                binding.textInputLayout.isEndIconVisible = false
                binding.usernameEdit.setError("Le credenziali non sono corrette!")
                binding.passwordEdit.setError("Le credenziali non sono corrette!")
            }
        }

    }

    private fun verificaCredenziali(username: String, password: String) : Boolean{
        val userExist = DataRepository.getUser(username)

        if (!userExist.username.equals("")){    //if user exixst
            if(userExist.password.equals(password)){    //if password is equal to saved login
                DataRepository.salvaUtenteCorrente(userExist)  //save current user in sharedPreferences
                saveStatusIfAdmin(userExist)
                return true;

            }else{ //if pawwsord is wrong
                binding.passwordEdit.setError("Password errata!")
            }

        }else{  //if user does not exist
            binding.usernameEdit.setError("Questo account non esiste!")
            return false
        }
        return false
    }



    private fun saveStatusIfAdmin(user : User){
        if(user.admin == 1){
            isAnAdmin = 1
        }else{
            isAnAdmin = 0
        }
    }

    private fun openHomeActivity(){
        val toHomeIntent = Intent(this, HomeActivity::class.java)

        Log.i("Status Admin::::::::::", ""+this.isAnAdmin )

        if(isAnAdmin == 1){
            toHomeIntent.putExtra("ISANADMIN", 1)
            Log.i("Status SI Admin::::::::::", ""+this.isAnAdmin )
        }else{
            toHomeIntent.putExtra("ISANADMIN", 0)
            Log.i("Status NO Admin::::::::::", ""+this.isAnAdmin )
        }
        startActivity(toHomeIntent)
        finish()
    }

    private fun openRegistrazioneActivity(){
        val registrazioneIntent = Intent(this, RegistrazioneActivity::class.java)
        startActivity(registrazioneIntent)
        finish()
    }
}