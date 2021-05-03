package com.example.eseritazionebonusium.usermanager

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.home.HomeActivity
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.User
import com.example.eseritazionebonusium.databinding.ActivityGestisciUtentiBinding

class UserManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGestisciUtentiBinding
    private lateinit var model: UserViewModelUserListManager


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: UserListAdapter

    private lateinit var userList: List<User>
    private val listener = View.OnClickListener {
        val user = it.tag as User

        when (it.id) {
            R.id.admin_management_row_button -> userStatusLayoutManager(user)
            R.id.cancella_utente_button -> deleteUserLayoutManager(user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestisci_utenti)

        model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModelUserListManager::class.java)


        userList = DataRepository.getUsersList()    //get the list of all users

        binding = DataBindingUtil.setContentView(this, R.layout.activity_gestisci_utenti)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)

        model.checkUserList().observe(this, Observer {
            mAdapter = UserListAdapter(model.getUserList(), listener)
            mRecyclerView.adapter = mAdapter
        })

        mRecyclerView.layoutManager = mLayoutManager


        binding.searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                filter(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
        })
    }

    fun filter(str: String) {
        val searchList: MutableList<User> = ArrayList()

        userList = DataRepository.getUsersList()

        for (elem in userList) {
            if (elem.username.contains(str)) { ///if contains text
                Log.i("Contenuto elemento in filter  ", elem.username)
                searchList.add(elem)
            }
        }
        mAdapter.updateList(searchList)
    }

    fun userStatusLayoutManager(user: User) {
        when (model.modifyUserStatus(user)) {
            UserViewModelUserListManager.VMListManagerReturnType.NOT_EXIST -> Log.i("userStatusLayoutManager", "return value NOT_EXIST")
            UserViewModelUserListManager.VMListManagerReturnType.ALREADY_ADMIN -> {
                Toast.makeText(this, user.username + "is already admin!", Toast.LENGTH_SHORT)
            }
            UserViewModelUserListManager.VMListManagerReturnType.ADMIN_OK -> {
                Toast.makeText(this, "" + user.username + " is now admin.", Toast.LENGTH_SHORT).show()
                mAdapter.updateList(DataRepository.getUsersList())
            }
            else -> Log.i("userStatusLayoutManager", "Unexpected return value")
        }
    }

    fun deleteUserLayoutManager(user: User) {
        when (model.deleteUser(user)) {
            UserViewModelUserListManager.VMListManagerReturnType.SELF_DELET_ERROR -> Toast.makeText(this, "Admin cannot delete himself", Toast.LENGTH_SHORT).show()
            UserViewModelUserListManager.VMListManagerReturnType.CANNOT_DELETE_ADMIN -> Toast.makeText(this, "Cannot delete another Admin", Toast.LENGTH_SHORT).show()
            UserViewModelUserListManager.VMListManagerReturnType.NOT_EXIST -> Log.i("userStatusLayoutManager", "L username non ha trovato corrispondenze nelle sharedPreferences")
            UserViewModelUserListManager.VMListManagerReturnType.DELETE_OK -> {
                Toast.makeText(this, "" + user.username + " deleted", Toast.LENGTH_SHORT).show()
                mAdapter.updateList(DataRepository.getUsersList())
            }
            else -> Log.i("userStatusLayoutManager", "Unexpected return value")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val homeActivityIntent: Intent = Intent(this, HomeActivity::class.java)
        homeActivityIntent.putExtra("ISANADMIN", 1)
        startActivity(homeActivityIntent)
        finish()
    }

}