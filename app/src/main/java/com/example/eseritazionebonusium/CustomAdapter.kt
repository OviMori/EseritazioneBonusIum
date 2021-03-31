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
        lateinit var mTextForDeleting : TextView
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
        val strUtenteInfo : String = listaUtenti.get(position).second
        val utente = Utente()
        utente.creaNuovoUtenteDaStringa(strUtenteInfo)

        viewHolder.mText.setText(utente.username)

        viewHolder.mButton.setOnClickListener {
            var elementoDaEliminare: Pair<String, String> = listaUtenti.get(position)
            Log.i("elemento selezionato key", elementoDaEliminare.first)
            Log.i("elemento selezionato value", elementoDaEliminare.second)

            cancellaUtente(elementoDaEliminare)
            notifyDataSetChanged()

        }
    }

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