package com.example.eseritazionebonusium.usermanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.User

class UserListAdapter(
    private var listaUtenti: List<User>,
    private val listener: View.OnClickListener
) : RecyclerView.Adapter<UserViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_row_item, viewGroup, false)
        return UserViewHolder(view, listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {

        viewHolder.bind(listaUtenti[position])

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
    }

    fun updateList(list : List<User>){
        listaUtenti = list
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listaUtenti.size

}