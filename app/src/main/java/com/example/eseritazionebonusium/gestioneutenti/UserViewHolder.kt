package com.example.eseritazionebonusium.gestioneutenti

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.User
import kotlinx.android.synthetic.main.user_row_item.view.*

class UserViewHolder(view: View, listener: View.OnClickListener) : RecyclerView.ViewHolder(view) {

    init {
        itemView.cancella_utente_button.setOnClickListener(listener)
        itemView.admin_management_row_button.setOnClickListener(listener)
    }

    fun bind(user: User) {
        itemView.cancella_utente_button.tag = user
        itemView.admin_management_row_button.tag = user

        itemView.utente_textview_row.text = user.username
        itemView.admin_management_row_button.text = if (user.isAdmin) "Admin" else "Imposta Admin"

    }
}