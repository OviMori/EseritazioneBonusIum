package com.example.eseritazionebonusium

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private var listaUtenti: List<Pair<String, String>>, private var sharePref : SharedPreferences) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var mImageResourceIcon : ImageView
        lateinit var mText : TextView
        lateinit var mButton : AppCompatButton

        init {
            // Define click listener for the ViewHolder's View.
            mImageResourceIcon = view.findViewById(R.id.utente_icon)
            mText = view.findViewById(R.id.utente_textview_row)
            mButton = view.findViewById(R.id.utente_row_button)
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        val stringaDatiUtente = listaUtenti.get(position).second
        val utente = Utente()
        utente.creaNuovoUtenteDaStringa(stringaDatiUtente)

        viewHolder.mText.text = utente.username

        var strUser: Pair<String, String> = listaUtenti.get(position)
        val user : Utente = Utente()
        user.creaNuovoUtenteDaStringa(strUser.second)

        Log.i("viewHolder Stampa Dati utente ", user.toString())
        if(user.admin == 1){
            viewHolder.mButton.text = "Admin"
        }


        viewHolder.mImageResourceIcon.setOnClickListener{
            var utenteDaModificare: Pair<String, String> = listaUtenti.get(position)

            cancellaUtente(utenteDaModificare)
            notifyDataSetChanged()
        }

        viewHolder.mButton.setOnClickListener {
            var utenteDaModificare: Pair<String, String> = listaUtenti.get(position)
            Log.i("elemento selezionato key", utenteDaModificare.first)
            Log.i("elemento selezionato value", utenteDaModificare.second)

            val utenteModificato: Utente? = modificaStatusUtente(utenteDaModificare)

            if(utenteModificato != null){
                if(viewHolder.mButton.text.equals("Imposta Admin")){
                    viewHolder.mButton.setText("Admin")
                }else{
                    viewHolder.mButton.setText("Imposta Admin")
                }

                listaUtenti = DataRepository.getUsersList() as List<Pair<String, String>>
                notifyDataSetChanged()
            }

        }


    }

    fun modificaStatusUtente(utenteDaModificare : Pair<String, String>) : Utente?{
        val selectedUser = Utente()
        selectedUser.creaNuovoUtenteDaStringa(utenteDaModificare.second)
        val userFromSharedPref : Utente

        if(DataRepository.userExist(selectedUser.username)){
            userFromSharedPref = DataRepository.getUser(selectedUser.username)

            if(userFromSharedPref.admin == 0){
                userFromSharedPref.admin = 1
            }else{
                userFromSharedPref.admin = 0
            }

            DataRepository.salvaUtente(userFromSharedPref)  //overrite user data
            return userFromSharedPref
        }else{
            return null
        }
    }

    /*fun modificaStatusUtente(utenteDaModificare : Pair<String, String>) : Utente{
        var edit : SharedPreferences.Editor = sharePref.edit()
        val utenteTemp = Utente()

        if(sharePref.contains(utenteDaModificare.first)){

            utenteTemp.creaNuovoUtenteDaStringa(utenteDaModificare.second)

            if(utenteTemp.admin == 1){
                utenteTemp.admin = 0
            }else{
                utenteTemp.admin = 1
            }

            val utenteModificato: String = utenteTemp.toString()

            edit.putString(utenteDaModificare.first, utenteModificato)  //aggiorno i valori della lista sharedPreferences

        }

        for(s in listaUtenti){
            Log.i("&&&&&&&&&&&&&&&&&&      ", s.second)
        }

        listaUtenti = sharePref.all.toList() as List<Pair<String, String>>


        for(s in listaUtenti){
            Log.i("&&&&&&&&&&&&&&&&&&      ", s.second)
        }

        return utenteTemp
    }*/

    fun cancellaUtente(elementoDaEliminare : Pair<String, String>){
        var edit : SharedPreferences.Editor = sharePref.edit()

        if(sharePref.contains(elementoDaEliminare.first)){
            edit.remove(elementoDaEliminare.first).apply()
        }

        listaUtenti = sharePref.all.toList() as List<Pair<String, String>>
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listaUtenti.size

}