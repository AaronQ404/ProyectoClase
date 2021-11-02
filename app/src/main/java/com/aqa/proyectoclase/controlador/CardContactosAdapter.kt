package com.aqa.proyectoclase.controlador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.modelo.User

class CardContactosAdapter(val contactos:Array<User>, val contexto: Context, val interfazContactos: InterfazContactos)
    : RecyclerView.Adapter<CardContactosAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {
        val txtUsename : TextView
        val imgPicContact : ImageView
            init{
                txtUsename = view.findViewById(R.id.txtUsernameContact)
                imgPicContact = view.findViewById(R.id.imgProfilePicContact)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_contacto, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = contactos[position]
        holder.txtUsename.text = user.username
    }

    override fun getItemCount(): Int {
        return contactos.size
    }


}