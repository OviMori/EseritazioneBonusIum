package com.example.eseritazionebonusium.gestioneutenti

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eseritazionebonusium.R
import com.example.eseritazionebonusium.User
import com.example.eseritazionebonusium.databinding.UserRowItemBinding

class UserViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    var mImageResourceIcon : ImageView = view.findViewById(R.id.utente_icon)
    var mText : TextView = view.findViewById(R.id.utente_textview_row)
    var mButton : AppCompatButton = view.findViewById(R.id.utente_row_button)


    fun bind(user: User){
        mText.setText(user.username)
    }

    fun setAdminText(user: User){
        mButton.setText("Admin")
    }

}