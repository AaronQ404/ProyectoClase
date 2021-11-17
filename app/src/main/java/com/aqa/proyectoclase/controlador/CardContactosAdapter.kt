package com.aqa.proyectoclase.controlador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.modelo.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class CardContactosAdapter(
        var contactos: ArrayList<User>,
        val contexto: Context,
        val itemClickListener: OnClickContacto)
    : RecyclerView.Adapter<CardContactosAdapter.ViewHolder>() {


    public interface OnClickContacto{
        fun onItemClick(pos: Int)
    }

    inner class ViewHolder(view: View, itemClickListener: OnClickContacto): RecyclerView.ViewHolder(view)  {

        val txtUsename : TextView
        val imgPicContact : ImageView
        val crdContacto : ConstraintLayout
            init{
                txtUsename = view.findViewById(R.id.txtUsernameContact)
                imgPicContact = view.findViewById(R.id.imgProfilePicContact)
                crdContacto = view.findViewById(R.id.crlContacto)
                crdContacto.setOnClickListener{itemClickListener.onItemClick(adapterPosition)}
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_contacto, parent, false)
        return ViewHolder(v,itemClickListener).listen{ pos: Int, type: Int ->
            val amigo = contactos[pos]
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = contactos[position]
        holder.txtUsename.text = user.username
        Glide.with(contexto).load(contactos[position]).placeholder(R.drawable.profile).into(holder.imgPicContact)
    }

    override fun getItemCount(): Int {
        return contactos.size
    }


    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }





}

