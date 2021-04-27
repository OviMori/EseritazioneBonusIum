package com.example.eseritazionebonusium

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.databinding.ActivityLoginBinding

import com.example.eseritazionebonusium.vm.UserViewModelLogIn

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: UserViewModelLogIn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModelLogIn::class.java)

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

    private fun verificaCredenziali(username: String, password: String) : Boolean {
        when (viewModel.verificaCredenzialiAccesso(username, password)) {
            0 -> return true
            -1 -> binding.usernameEdit.setError("Questo account non esiste!")
            -2 -> binding.passwordEdit.setError("Password errata!")
        }
        return false
    }

    private fun openHomeActivity(){
        val toHomeIntent = Intent(this, HomeActivity::class.java)

        if(viewModel.isAnAdmin.equals(true)){
            toHomeIntent.putExtra("ISANADMIN", 1)
        }else{
            toHomeIntent.putExtra("ISANADMIN", 0)
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