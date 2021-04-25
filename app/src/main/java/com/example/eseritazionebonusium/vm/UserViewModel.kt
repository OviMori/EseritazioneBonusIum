package com.example.eseritazionebonusium.vm

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModel : ViewModel(){
    private var userList = mutableListOf<User>()
    private val userListLiveData = MutableLiveData<List<User>>()

    init{
        userList = DataRepository.getUsersList() as MutableList<User>
        userListLiveData.value = userList
    }

    fun checkUserList(): MutableLiveData<List<User>>{
        return userListLiveData
    }

    fun getUserList(): List<User>{
        return userList
    }
}