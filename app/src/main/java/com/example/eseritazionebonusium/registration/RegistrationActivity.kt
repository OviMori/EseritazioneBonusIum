package com.example.eseritazionebonusium.registration

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.databinding.ActivityRegistrazioneBinding
import com.example.eseritazionebonusium.login.LoginActivity
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrazioneBinding
    private lateinit var model: UserViewModelRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrazione)

        model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                UserViewModelRegistration::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registrazione)

        binding.layoutInputBirthEdit.setOnClickListener {
            showDataPicker()
        }



        binding.registrationButton.setOnClickListener {
            var newUsername: String = binding.layoutInputUsername.editText?.text.toString()
            var newPassword: String = binding.layoutInputPassword.editText?.text.toString()
            var newConfermaPassword: String = binding.layoutInputConfirmPassword.editText?.text.toString()
            var newCitta: String = binding.layoutInputCity.editText?.text.toString()
            var newDataNascita: String = binding.layoutInputBirth.editText?.text.toString()

            registrationLayoutManager(newUsername, newPassword, newConfermaPassword, newCitta, newDataNascita)
        }
    }

    fun registrationLayoutManager(
            username: String,
            password: String,
            confirmPassword: String,
            city: String,
            birth: String
    ) {
        when (model.registrationProceedings(username, password, confirmPassword, city, birth)) {
            UserViewModelRegistration.RegistrationReturnValue.INVALID_USERNAME -> binding.layoutInputUsername.setError("Name cannot be blank! ")
            UserViewModelRegistration.RegistrationReturnValue.INVALID_PASSWORD -> binding.layoutInputConfirmPassword.setError("Password cannot be blank! ")
            UserViewModelRegistration.RegistrationReturnValue.USER_ALREADY_EXIST -> binding.layoutInputUsername.setError("This username is already being used by another account")
            UserViewModelRegistration.RegistrationReturnValue.INVALID_PASSWORD_MATCH -> binding.layoutInputPassword.setError("Password entered do not match!")
            UserViewModelRegistration.RegistrationReturnValue.USER_DATA_SAVED -> {
                Toast.makeText(this, "Account saving...", Toast.LENGTH_SHORT).show()
                returnToLogInActivity()
            }
            else -> Log.i("Unexpected return value:", "registtrationProceed")
        }
    }

    fun returnToLogInActivity() {
        val toLoginActivity = Intent(this, LoginActivity::class.java)
        startActivity(toLoginActivity)
        finish()
    }


    @SuppressLint("SetTextI18n")
    private fun showDataPicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in TextView
            binding.layoutInputBirth.editText?.setText("" + dayOfMonth + ", " + month + ", " + year)
        }, year, month, day)
        dpd.show()
    }

}

