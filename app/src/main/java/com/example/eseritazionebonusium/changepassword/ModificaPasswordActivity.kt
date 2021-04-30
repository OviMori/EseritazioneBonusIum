package com.example.eseritazionebonusium.changepassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.home.HomeActivity
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.databinding.ActivityModificaPasswordBinding

class ModificaPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModificaPasswordBinding
    private lateinit var viewModel: UserViewModelPasswordModify

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifica_password)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_modifica_password)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModelPasswordModify::class.java)

        setCurrentUserData()

        binding.tornaHomeButton.setOnClickListener {
            val homeIntent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

        binding.aggiornaPasswordButton.setOnClickListener {
            val newPassword: String = binding.passwordEdit.text.toString()
            val newPasswordConferma : String = binding.passwordEditConfirm.text.toString()

            if(viewModel.passwordsFitTogether(newPassword, newPasswordConferma)){
                if(checkMinInput(newPassword)){
                    if(!viewModel.aggiornaDatiUtente(newPassword)){
                        Toast.makeText(this, "Cannot modify admin credential", Toast.LENGTH_SHORT).show()
                    }
                    setCurrentUserData()
                    Toast.makeText(this, " Password updated. ", Toast.LENGTH_SHORT).show()
                }

            }else{
                binding.textInputLayoutModifyPassword.isEndIconVisible = false
                binding.passwordEdit.setError("Le password inserite non corrispondono!")
            }
        }
    }

    fun checkMinInput(newPassword: String) : Boolean{
        if(newPassword.trim().isBlank()){
            binding.textInputLayoutModifyPasswordConfirm.isEndIconVisible = false
            binding.passwordEdit.setError("Password cannot be blank! ")
            return false
        }
        return true
    }

    //TODO(Change datarepository call to to ViewModelCall)
    fun aggiornaDatiUtente( newPassword : String){
        val currentUser = DataRepository.getCurrentUser()

        if(currentUser != null){
            if(!currentUser.username.equals("admin")){  //if is not the admin account
                DataRepository.saveNewPassword(newPassword)
            }else{
                Toast.makeText(this, "Cannot modify admin credential", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun setCurrentUserData(){
        val user = viewModel.getCurrentUser()
        if( user != null){
            binding.modificaPasswordUsernameCorrente.setText(user.username)
            binding.modificaPasswordPasswordCorrente.setText(user.password)
        }
    }
}