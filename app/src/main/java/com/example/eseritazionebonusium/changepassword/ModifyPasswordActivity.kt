package com.example.eseritazionebonusium.changepassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.databinding.ActivityModificaPasswordBinding
import com.example.eseritazionebonusium.home.HomeActivity

class ModifyPasswordActivity : AppCompatActivity() {

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
            val toHomeIntent = Intent(this, HomeActivity::class.java)
            startActivity(toHomeIntent)
            finish()
        }

        binding.updatePasswordButton.setOnClickListener {
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
                binding.passwordEdit.setError("The passwords entered do not match!")
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

    private fun setCurrentUserData(){
        val user = viewModel.getCurrentUser()
        if( user != null){
            binding.modificaPasswordUsernameCorrente.setText(user.username)
            binding.modificaPasswordPasswordCorrente.setText(user.password)
        }
    }
}