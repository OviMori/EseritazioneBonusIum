package com.example.eseritazionebonusium

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.minusAssign
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.databinding.ActivityGestisciUtentiBinding

class GestioneUtentiActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGestisciUtentiBinding
    private lateinit var listKeys : Map<String, String>
    private lateinit var listaToList :List<Pair<String, String>>

    private lateinit var  mRecyclerView: RecyclerView
    private lateinit var  mLayoutManager: LinearLayoutManager
    private lateinit var  mAdapter : CustomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestisci_utenti)

        listKeys = HashMap<String, String>()


        getAllKeysSharedPreferences()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gestisci_utenti)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        listaToList = listKeys.toList()
        mAdapter = CustomAdapter(listaToList, this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter


        binding.searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
        })
    }

    fun filter(str: String){
        var searchList :MutableList<Pair<String, String>> = ArrayList()

        for(elem :Pair<String, String> in listaToList){
            if(elem.first.contains(str)){ ///if contains text
                Log.i("Contenuto elemento   ", elem.first)
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

    private fun getAllKeysSharedPreferences(){

        listKeys = DataRepository.getUsersList()
    }
}