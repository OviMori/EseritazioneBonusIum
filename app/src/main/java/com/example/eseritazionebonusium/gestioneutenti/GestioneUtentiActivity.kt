package com.example.eseritazionebonusium.gestioneutenti

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.HomeActivity
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.User
import com.example.eseritazionebonusium.databinding.ActivityGestisciUtentiBinding

class GestioneUtentiActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGestisciUtentiBinding

    private lateinit var  mRecyclerView: RecyclerView
    private lateinit var  mLayoutManager: LinearLayoutManager
    private lateinit var  mAdapter : UserListAdapter

    private lateinit var userList: List<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestisci_utenti)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_gestisci_utenti)
        userList = DataRepository.getUsersList()    //get the list of all users

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = UserListAdapter(userList, this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter


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