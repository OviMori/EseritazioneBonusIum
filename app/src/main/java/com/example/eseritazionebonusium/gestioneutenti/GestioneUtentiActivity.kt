package com.example.eseritazionebonusium.gestioneutenti

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
import com.example.eseritazionebonusium.HomeActivity
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.User
import com.example.eseritazionebonusium.databinding.ActivityGestisciUtentiBinding
import com.example.eseritazionebonusium.vm.Injector
import com.example.eseritazionebonusium.vm.UserViewModel

class GestioneUtentiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGestisciUtentiBinding

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: UserListAdapter

    private lateinit var userList: List<User>
    private val listener = View.OnClickListener {
        val user = it.tag as User

        when (it.id) {
            R.id.admin_management_row_button -> modificaStatusUtente(user)
            R.id.cancella_utente_button -> cancellaUtente(user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestisci_utenti)

        val repo = Injector.provideMyUserViewModel()
        val model = ViewModelProvider(this, repo).get(UserViewModel::class.java)


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

    fun modificaStatusUtente(selectedUser: User): Boolean {
        if (DataRepository.userExist(selectedUser.username)) {
            if (!selectedUser.isAdmin) {
                DataRepository.setAdmin(selectedUser)
                Toast.makeText(this, "" + selectedUser.username + " is now admin.", Toast.LENGTH_SHORT).show()
                mAdapter.updateList(DataRepository.getUsersList())
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    fun cancellaUtente(elementoDaEliminare: User): Boolean {
        if (!checkifIsCurrentAdmin(elementoDaEliminare)) {
            if (!elementoDaEliminare.isAdmin) {
                if (!DataRepository.dropUser(elementoDaEliminare)) {    //if username does not existi in sharedPreferences
                    Log.i("cancellaUtente", "L username non ha trovato corrispondenze nelle sharedPreferences")
                    return false
                } else {
                    Toast.makeText(this, "" + elementoDaEliminare.username + " deleted", Toast.LENGTH_SHORT).show()
                    mAdapter.updateList(DataRepository.getUsersList())
                    return true
                }
            } else {
                Toast.makeText(this, "Cannot delete another Admin", Toast.LENGTH_SHORT).show()
                return false
            }

        } else {
            Toast.makeText(this, "Admin cannot delete himself", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    fun checkifIsCurrentAdmin(selectedUser: User): Boolean {
        val currentUser: User = DataRepository.getCurrentUser()

        if (currentUser.toString().equals(selectedUser.toString())) { //if is the same user
            return true
        }
        return false
    }

    fun filter(str: String){
        val searchList :MutableList<User> = ArrayList()

        userList = DataRepository.getUsersList()

        for(elem in userList){
            if(elem.username.contains(str)){ ///if contains text
                Log.i("Contenuto elemento in filter  ", elem.username)
                searchList.add(elem)
            }
        }
        mAdapter.updateList(searchList)
    }



    override fun onBackPressed() {
        super.onBackPressed()
        val homeActivityIntent : Intent = Intent(this, HomeActivity::class.java)
        homeActivityIntent.putExtra("ISANADMIN", 1)
        startActivity(homeActivityIntent)
        finish()
    }

}