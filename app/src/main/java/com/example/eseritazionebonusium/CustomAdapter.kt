package com.example.eseritazionebonusium

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private var listaUtenti: List<Pair<String, String>>, context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    val myMainApp : MainApplication = MainApplication()
    val context = context
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mImageResourceIcon : ImageView
        var mText : TextView
        var mButton : AppCompatButton

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
        initDataViewHolder(viewHolder, position)

        viewHolder.mImageResourceIcon.setOnClickListener{
            var utenteDaCancellareMap: Pair<String, String> = listaUtenti.get(position)


            cancellaUtente(utenteDaCancellareMap)
            notifyDataSetChanged()
        }

        viewHolder.mButton.setOnClickListener {
            requestChangeStatusUser(viewHolder, position)
            notifyDataSetChanged()
        }


    }

    fun updateList(list : List<Pair<String, String>>){
        listaUtenti = list
        Log.i("Dimensione lista parametro ", list.size.toString())
        notifyDataSetChanged()
    }

    fun initDataViewHolder(viewHolder : ViewHolder, position: Int){
        val stringaDatiUtente = listaUtenti.get(position).second
        val utente = Utente()
        utente.creaNuovoUtenteDaStringa(stringaDatiUtente)

        viewHolder.mText.text = utente.username

        var strUser: Pair<String, String> = listaUtenti.get(position)
        val user = Utente()
        user.creaNuovoUtenteDaStringa(strUser.second)

        Log.i("viewHolder Stampa Dati utente ", user.toString())
        if(user.admin == 1){
            viewHolder.mButton.text = "Admin"
        }
    }

    fun requestChangeStatusUser(viewHolder: ViewHolder, position: Int){
        var utenteDaModificareMap: Pair<String, String> = listaUtenti.get(position)

        Log.i("elemento selezionato key", utenteDaModificareMap.first)
        Log.i("elemento selezionato value", utenteDaModificareMap.second)

        val selectedUser = Utente()
        selectedUser.creaNuovoUtenteDaStringa(utenteDaModificareMap.second)

        if(!checkifIsCurrentAdmin(selectedUser)){
            val utenteModificato: Utente? = modificaStatusUtente(selectedUser)

            if(utenteModificato != null){
                if(viewHolder.mButton.text.equals("Imposta Admin")){
                    viewHolder.mButton.setText("Admin")
                }else{
                    viewHolder.mButton.setText("Imposta Admin")
                }

                listaUtenti = DataRepository.getUsersList().toList() //si puo' fare??
            }
        }else{
            Toast.makeText(context, "Admin cannot modify himself", Toast.LENGTH_SHORT).show()
        }


    }

    fun checkifIsCurrentAdmin(selectedUser : Utente) : Boolean{
        val currentUser : Utente = DataRepository.getCurrentUser()

        if(currentUser.toString().equals(selectedUser.toString())){ //if is the same user
            return true
        }
        return false
    }

    fun modificaStatusUtente(selectedUser : Utente) : Utente?{
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

    fun cancellaUtente(elementoDaEliminare : Pair<String, String>){
        val utente = Utente()
        utente.creaNuovoUtenteDaStringa(elementoDaEliminare.second)

        if(!checkifIsCurrentAdmin(utente)){
            if(!DataRepository.dropUser(elementoDaEliminare.first)){    //if username does not existi in sharedPreferences
                Log.i("cancellaUtente", "L username non ha trovato corrispondenze nelle sharedPreferences")
            }
        }else{
            Toast.makeText(context, "Admin cannot delete himself", Toast.LENGTH_SHORT).show()
        }
        listaUtenti = DataRepository.getUsersList().toList()
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listaUtenti.size

}