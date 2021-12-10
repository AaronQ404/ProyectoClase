package com.aqa.proyectoclase.vista.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.CardContactosAdapter
import com.aqa.proyectoclase.controlador.MainController
import com.aqa.proyectoclase.databinding.FragmentContactBinding
import com.aqa.proyectoclase.modelo.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class ContactFragment : Fragment(), CardContactosAdapter.OnClickContacto {
    private lateinit var auth:FirebaseAuth
    private lateinit var myRef:DatabaseReference
    private lateinit var contexto:Context
    lateinit var  userId : String
    private lateinit var user :FirebaseUser
    private lateinit var users : ArrayList<User>
    private lateinit var contactosAdapter: CardContactosAdapter
    val database = Firebase.database
    private lateinit var binding: FragmentContactBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        MainController.Instance.changeBMenuVisibility(activity,true)
        auth = Firebase.auth
        contexto = this.requireContext()
        users  = ArrayList()
        myRef = database.getReference("Friends").child(auth.currentUser?.uid.toString())
        user = auth.currentUser!!
        val provId = user.providerData.toString()
        // Inflate the layout for this fragment
        binding = FragmentContactBinding.inflate(inflater, container, false)
        getFriends(myRef)
        var fbtnAddContact =  binding.fbtnAddContact
        fbtnAddContact.setOnClickListener { view ->
            MainController.Instance.changeFragent(activity,R.id.frmMainFrame,SearchContactFragment(),null)
            Toast.makeText(context,"Test",Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    fun getFriends(myRef : DatabaseReference){
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(u in snapshot.children){
                    val userContact = database.getReference("Users").child(u.key.toString())
                    getFriend(userContact)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun getFriend(myRef: DatabaseReference){
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val contacto:User? = snapshot.getValue<User>() // com.google.firebase.database.DatabaseException: Expected a List while deserializing, but got a class java.util.HashMap
                if (contacto != null) {
                    users.add(contacto)
                    loadRecycler()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    override fun onItemClick(pos: Int) {
        val chatFra: ChatFragment = ChatFragment()
        val bundle:Bundle = Bundle()
        val friend = users.get(pos)
        chatFra.arguments = bundle
        bundle.putString("uidF",friend.uid)
        Toast.makeText(context, friend.username, Toast.LENGTH_LONG).show()
            MainController.Instance.changeFragent(activity,R.id.frmMainFrame,ChatFragment())
    }

    fun loadRecycler(){
        if(users.size >0){
            contactosAdapter = CardContactosAdapter(users,contexto,this)
            binding.rclContacts.adapter = contactosAdapter
            binding.rclContacts.layoutManager = LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL ,false)
        }else{
            userId="No tienes amigos"
        }
    }
}