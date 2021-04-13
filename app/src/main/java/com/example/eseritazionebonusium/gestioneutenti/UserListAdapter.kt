package com.example.eseritazionebonusium.gestioneutenti

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.DataRepository
import com.example.eseritazionebonusium.MainApplication
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.User

class UserListAdapter(private var listaUtenti: List<User>, private val context: Context) : RecyclerView.Adapter<UserViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_row_item, viewGroup, false)
        return UserViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        viewHolder.bind(listaUtenti.get(position))
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.mImageResourceIcon.setOnClickListener{
            var utenteDaCancellare: User = listaUtenti.get(position)

            if(cancellaUtente(utenteDaCancellare)){

                //listaUtenti = DataRepository.getUsersList() //verificare se funziona anche con questa riga commentata
                notifyDataSetChanged()
            }
        }

        viewHolder.mButton.setOnClickListener {
            if(requestChangeStatusUser(listaUtenti.get(position))){
                val userModificato: User? = modificaStatusUtente(listaUtenti.get(position))

                if(userModificato != null){
                    if(DataRepository.isAdmin(listaUtenti.get(position))){
                        viewHolder.setAdminText(listaUtenti.get(position))
                    }
                    listaUtenti = DataRepository.getUsersList() //si puo' fare??
                }
            }
            notifyDataSetChanged()
        }

    }

    fun updateList(list : List<User>){
        listaUtenti = list
        notifyDataSetChanged()
    }

    fun initDataViewHolder(viewHolder : UserViewHolder, position: Int){
        val user = listaUtenti.get(position)
        viewHolder.mText.text = user.username

        if(user.admin == 1){
            viewHolder.mButton.text = "Admin"
        }else{
            viewHolder.mButton.text = "Imposta Admin"
        }
    }

    fun requestChangeStatusUser(selectedUser: User) : Boolean{

        if(!checkifIsCurrentAdmin(selectedUser)){
            return true
        }else{
            Toast.makeText(context, "Admin cannot modify himself", Toast.LENGTH_SHORT).show()
            return false
        }


    }

    fun checkifIsCurrentAdmin(selectedUser : User) : Boolean{
        val currentUser : User = DataRepository.getCurrentUser()

        if(currentUser.toString().equals(selectedUser.toString())){ //if is the same user
            return true
        }
        return false
    }

    fun modificaStatusUtente(selectedUser : User) : User?{
        val userFromSharedPref : User

        if(DataRepository.userExist(selectedUser.username)){
            userFromSharedPref = DataRepository.getUser(selectedUser.username)

            if(userFromSharedPref.admin == 0){
                userFromSharedPref.admin = 1
                Toast.makeText(context, ""+userFromSharedPref.username+" is now admin.", Toast.LENGTH_SHORT).show()
            }else{
                userFromSharedPref.admin = 0
            }


            DataRepository.salvaUtente(userFromSharedPref)  //overrite user data
            return userFromSharedPref
        }else{
            return null
        }
    }

    fun chekIfUserSelectedIsAdmin(selectedUser: User): Boolean{
        if(selectedUser.admin == 1){
            return true
        }
        return false
    }

    fun cancellaUtente(elementoDaEliminare : User): Boolean{
        if(!checkifIsCurrentAdmin(elementoDaEliminare)){
            if(!chekIfUserSelectedIsAdmin(elementoDaEliminare)){
                if(!DataRepository.dropUser(elementoDaEliminare)){    //if username does not existi in sharedPreferences
                    Log.i("cancellaUtente", "L username non ha trovato corrispondenze nelle sharedPreferences")
                    return false
                }else{
                    Toast.makeText(context, ""+elementoDaEliminare.username+" deleted", Toast.LENGTH_SHORT).show()
                    return true
                }
            }else{
                Toast.makeText(context, "Cannot delete another Admin", Toast.LENGTH_SHORT).show()
                return false
            }

        }else{
            Toast.makeText(context, "Admin cannot delete himself", Toast.LENGTH_SHORT).show()
            return false
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listaUtenti.size

}