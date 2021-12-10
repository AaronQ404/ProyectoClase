package com.aqa.proyectoclase.controlador

import android.content.Context
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.modelo.Post
import com.aqa.proyectoclase.modelo.User
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class CardPostAdapter(var posts : ArrayList<Post>,
                        var contexto : Context
): RecyclerView.Adapter<CardPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_post, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post :Post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {
        val txtTitulo : TextView = view.findViewById(R.id.txtTitle)
        val txtContenido : TextView = view.findViewById(R.id.txtContent)
        val imgProfilePic : CircleImageView = view.findViewById(R.id.imgProfilePic)
        val txtUserName : TextView = view.findViewById(R.id.txtProfileName)
        val imgPost : ImageView = view.findViewById(R.id.imgContent)
        fun bind(post : Post){
            Firebase.database.getReference("Users").child(post.userId).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue<User>()!!
                    txtUserName.text = user.username
                    Glide.with(contexto).load(Base64.decode(user.profilePic, Base64.DEFAULT)).placeholder(R.drawable.profile).into(imgProfilePic)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            txtTitulo.text = post.title
            txtContenido.text = post.content
            if(post.image != null || post.image?.trim().equals("")){
                Glide.with(contexto).load(Base64.decode(post.image, Base64.DEFAULT)).placeholder(R.drawable.profile).into(imgPost)
            }else{
                imgPost.visibility = View.GONE
            }
        }
    }

}