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

    object Foo {
        @JvmStatic var counter: Int = 0

        fun set(value : Int){
            counter += value
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
        val initUtente: Utente = Utente()
        utente.creaNuovoUtenteDaStringa(strUtenteInfo)

        viewHolder.mText.setText(utente.username)

        if(Foo.counter <= listaUtenti.size){
            val initUtenteString: String = listaUtenti.get(Foo.counter).second
            initUtente.creaNuovoUtenteDaStringa(initUtenteString)

            if(initUtente.admin == 1){
                viewHolder.mButton.setText("Admin")
            }else{
                viewHolder.mButton.setText("Imposta Admin")
            }
        }


        viewHolder.mButton.setOnClickListener {
            var utenteDaModificare: Pair<String, String> = listaUtenti.get(position)
            Log.i("elemento selezionato key", utenteDaModificare.first)
            Log.i("elemento selezionato value", utenteDaModificare.second)

            val utenteModificato: Utente = modificaStatusUtente(utenteDaModificare)

            if(utenteModificato.admin == 1){
                viewHolder.mButton.setText("Admin")
            }else{
                viewHolder.mButton.setText("Imposta Admin")
            }

            notifyDataSetChanged()

        }
    }

    fun modificaStatusUtente(utenteDaModificare : Pair<String, String>) : Utente{
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

        listaUtenti = sharePref.all.toList() as List<Pair<String, String>>

        return utenteTemp
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listaUtenti.size

}