package com.example.eseritazionebonusium.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelUserListManager : ViewModel() {

    private var userList = DataRepository.getUsersList() as MutableList<User>
    private val userListLiveData = MutableLiveData<List<User>>(userList)

    fun checkUserList(): MutableLiveData<List<User>> {
        return userListLiveData
    }

    fun getUserList(): List<User> {
        return userList
    }
}