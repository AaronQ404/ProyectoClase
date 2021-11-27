package com.aqa.proyectoclase.controlador

import android.content.Context
import android.util.Base64
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
class CardChatContactosAdapter(
        var contactos: ArrayList<User>,
        val contexto: Context,
        val itemClickListener: OnClickContacto)
    : RecyclerView.Adapter<CardChatContactosAdapter.ViewHolder>() {


    public interface OnClickContacto{
        fun onItemClick(pos: Int)
    }

    inner class ViewHolder(view: View, itemClickListener: OnClickContacto): RecyclerView.ViewHolder(view)  {

        val txtUsename : TextView
        val txtTimeContact: TextView
        val txtLastMessage: TextView
        val imgPicContact : CircleImageView
        val crdContacto : ConstraintLayout
            init{
                txtUsename = view.findViewById(R.id.txtUsernameContact)
                txtTimeContact = view.findViewById(R.id.txtTimeContact)
                txtLastMessage = view.findViewById(R.id.txtLastMessage)
                imgPicContact = view.findViewById(R.id.imgProfilePicContact)
                crdContacto = view.findViewById(R.id.crlContacto)
                crdContacto.setOnClickListener{itemClickListener.onItemClick(adapterPosition)}
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_contacto, parent, false)
        return ViewHolder(v,itemClickListener).listen{ pos: Int, type: Int ->}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = contactos[position]
        holder.txtUsename.text = user.username
        val myRef:DatabaseReference = Firebase.database.getReference("Members")
        myRef.equalTo(user.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                holder.txtLastMessage.text = loadLastMessage(snapshot.getValue<String>().toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        Glide.with(contexto).load(Base64.decode(contactos[position].profilePic,Base64.DEFAULT)).placeholder(R.drawable.profile).into(holder.imgPicContact)
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

    fun loadLastMessage(chatId: String): String{
        var lastMessage : String = ""
        val myRef: DatabaseReference = Firebase.database.getReference("Chats").child(chatId)
        myRef.child("lastMessage").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                lastMessage = snapshot.getValue<String>().toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return lastMessage
    }
}


