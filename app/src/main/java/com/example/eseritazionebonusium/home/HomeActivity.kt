package com.example.eseritazionebonusium.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.changepassword.ModifyPasswordActivity
import com.example.eseritazionebonusium.databinding.ActivityHomeBinding
import com.example.eseritazionebonusium.usermanager.UserManagerActivity
import com.example.eseritazionebonusium.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: UserViewModelHome

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModelHome::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)



        if(viewModel.isAnAdmin){
            //mostra layout admin
            binding.adminIconLayout.visibility = View.VISIBLE
            binding.userManagerButton.visibility = View.VISIBLE
        }else{
            //nascondi layout admin
            binding.adminIconLayout.visibility = View.GONE
            binding.userManagerButton.visibility = View.GONE
        }

        setCurrentUserDate()

        binding.homeTopName.text = "Welcome "+ viewModel.getCurrentUser()?.username


        binding.userManagerButton.setOnClickListener{
            val toUsersManagerActivity  = Intent(this, UserManagerActivity::class.java)
            startActivity(toUsersManagerActivity)
            finish()
        }


        binding.modifyPasswordButton.setOnClickListener{
            val logoutIntent = Intent(this, ModifyPasswordActivity::class.java)
            startActivity(logoutIntent)
            finish()
        }

        binding.logoutButton.setOnClickListener{
            val logoutIntent : Intent  = Intent(this, LoginActivity::class.java)
            startActivity(logoutIntent)
            finish()
        }

    }

    private fun setCurrentUserDate(){
        val currentUser = viewModel.getCurrentUser()

        Log.i("Current User: ", currentUser.toString())

        binding.homeUsername.setText(currentUser?.username)
        binding.homePassword.setText(currentUser?.password)
        binding.homeCity.setText(currentUser?.city)
        binding.homeDataNascita.setText(currentUser?.dataNascita)
    }
}