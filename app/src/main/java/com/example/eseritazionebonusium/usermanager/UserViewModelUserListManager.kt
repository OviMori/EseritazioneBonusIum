package com.example.eseritazionebonusium.usermanager

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.User

class UserViewModelUserListManager : ViewModel() {

    private var userList = DataRepository.getUsersList() as MutableList<User>
    private val userListLiveData = MutableLiveData<List<User>>()

    init{
        userListLiveData.value = userList   //sul portatile dava problemi se passato come parametro di MutableLiveData, chiedere quale import avevamo usato su fisso
    }

    fun checkUserList(): MutableLiveData<List<User>> {
        return userListLiveData
    }

    fun getUserList(): List<User> {
        return userList
    }

    fun modifyUserStatus(selectedUser: User): VMListManagerReturnType {
        if (DataRepository.userExist(selectedUser)) {
            if (!selectedUser.isAdmin) {
                DataRepository.setAdmin(selectedUser)
                return VMListManagerReturnType.ADMIN_OK
            } else {
                return VMListManagerReturnType.ALREADY_ADMIN
            }
        } else {
            return VMListManagerReturnType.NOT_EXIST
        }
    }

    fun deleteUser(elementoDaEliminare: User): VMListManagerReturnType {
        if (!checkifIsCurrentUser(elementoDaEliminare)) {
            if (!elementoDaEliminare.isAdmin) {
                if (!DataRepository.dropUser(elementoDaEliminare)) {    //if username does not existi in sharedPreferences
                    return VMListManagerReturnType.NOT_EXIST
                } else {
                    return VMListManagerReturnType.DELETE_OK
                }
            } else {
                return VMListManagerReturnType.CANNOT_DELETE_ADMIN
            }
        } else {
            return VMListManagerReturnType.SELF_DELET_ERROR
        }
    }

    fun checkifIsCurrentUser(selectedUser: User): Boolean {
        val currentUser: User? = DataRepository.getCurrentUser()

        if (currentUser != null && DataRepository.compareUsers(currentUser, selectedUser)) { //if is the same user
            return true
        }
        return false
    }

    enum class VMListManagerReturnType {
        CANNOT_DELETE_ADMIN,
        NOT_EXIST,
        ADMIN_OK,
        ALREADY_ADMIN,
        SELF_DELET_ERROR,
        DELETE_OK
    }
}