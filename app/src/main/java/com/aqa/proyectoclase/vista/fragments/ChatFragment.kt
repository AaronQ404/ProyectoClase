package com.aqa.proyectoclase.vista.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.ChatAddapter
import com.aqa.proyectoclase.controlador.MainController
import com.aqa.proyectoclase.databinding.FragmentChatBinding
import com.aqa.proyectoclase.modelo.Message
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.security.Timestamp


class ChatFragment : Fragment() {

    private lateinit var myRef: DatabaseReference
    private var mensajes: ArrayList<Message> = ArrayList()
    private val user: FirebaseUser = Firebase.auth.currentUser!!
    private val uidF: String? = bundleOf().getString("uidF")
    private lateinit var chatAdapter: ChatAddapter
    private lateinit var binding: FragmentChatBinding
    private lateinit var contexto: Context
    private lateinit var fbtnEnviar: FloatingActionButton
    private lateinit var idChat:String
    private lateinit var txtMensaje: EditText
    private val database:FirebaseDatabase = Firebase.database

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        MainController.Instance.changeBMenuVisibility(activity,false)

        contexto = this.requireContext()
        binding = FragmentChatBinding.inflate(inflater, container, false)
        fbtnEnviar = binding.fbtnEnviar
        txtMensaje = binding.etxtMensaje
        fbtnEnviar.setOnClickListener(View.OnClickListener {
            enviarMensaje()
            txtMensaje.text.clear()
            binding.rclChat.scrollToPosition(mensajes.size - 1)
        })
        myRef = database.getReference("Members")
        myRef.equalTo(user.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myRef.equalTo(uidF).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (a in snapshot.children) {
                            Toast.makeText(contexto, a.key, Toast.LENGTH_SHORT).show()
                            idChat = a.key.toString()
                            cargarMensajes(a.key)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return binding.root
    }

    fun cargarMensajes(myRef: String?) {
        val ref = database.getReference("Messages").child(myRef.toString()).orderByChild("timestamp")


        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                mensajes.add(snapshot.getValue<Message>()!!)
                loadRecycer()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadRecycer() {
        chatAdapter = ChatAddapter(mensajes, user.uid)
        binding.rclChat.adapter = chatAdapter
        binding.rclChat.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }

    fun enviarMensaje(){
        val nMensaje = "m"+(mensajes.size+1)
        database.getReference("Messages").child(idChat).
        child(nMensaje).setValue(Message(user.uid,txtMensaje.text.toString(), System.currentTimeMillis()))
        val refChat :DatabaseReference = database.getReference("Chats").child(idChat)
        refChat.child("lastMessage").setValue(txtMensaje.text.toString())
        refChat.child("timestamp").setValue(System.currentTimeMillis())
        refChat.child("lastSender").setValue(user.uid)
    }
}