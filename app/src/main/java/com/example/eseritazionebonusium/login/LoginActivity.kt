package com.example.eseritazionebonusium.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.home.HomeActivity
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.registration.RegistrationActivity
import com.example.eseritazionebonusium.databinding.ActivityLoginBinding

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

        binding.registrationButton.setOnClickListener{
            openRegistrationActivity()
        }

        binding.inputLayoutPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                binding.textInputLayout.isEndIconVisible = true
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.inputLayoutUsername.editText?.setError(null)
            }
        })

        binding.accessButton.setOnClickListener{
            var usernameInput = binding.inputLayoutUsername.editText?.text.toString()
            var passwordInput = binding.inputLayoutPassword.editText?.text.toString()
            if(checkCredentials(usernameInput, passwordInput)){
                openHomeActivity()
            }else{
//                binding.textInputLayout.isEndIconVisible = false
                binding.inputLayoutUsername.editText?.setError("Le credenziali non sono corrette!")
                binding.inputLayoutPassword.editText?.setError("Le credenziali non sono corrette!")
            }
        }

    }

    private fun checkCredentials(username: String, password: String) : Boolean {
        when (viewModel.doLogin(username, password)) {
            UserViewModelLogIn.LoginResult.USER_LOGGED -> return true
            UserViewModelLogIn.LoginResult.USER_NOT_FOUND -> binding.inputLayoutUsername.editText?.setError("Questo account non esiste!")
            UserViewModelLogIn.LoginResult.WRONG_PASSWORD -> binding.inputLayoutPassword.editText?.setError("Password errata!")
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

    private fun openRegistrationActivity(){
        val toRegistrationIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(toRegistrationIntent)
        finish()
    }
}