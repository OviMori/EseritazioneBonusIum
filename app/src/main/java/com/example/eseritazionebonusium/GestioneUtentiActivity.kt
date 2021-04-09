package com.example.eseritazionebonusium

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.databinding.ActivityGestisciUtentiBinding

class GestioneUtentiActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGestisciUtentiBinding
    private lateinit var listKeys : Map<String, String>

    private lateinit var  mRecyclerView: RecyclerView
    private lateinit var  mLayoutManager: LinearLayoutManager
    private lateinit var  mAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestisci_utenti)

        listKeys = HashMap<String, String>()

        getAllKeysSharedPreferences()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gestisci_utenti)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        val listaToList :List<Pair<String, String>>  = listKeys.toList()
        mAdapter = CustomAdapter(listaToList, DataRepository.getSharedPref())
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
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