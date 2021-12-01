package com.aqa.proyectoclase.vista.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.CardContactosAdapter
import com.aqa.proyectoclase.databinding.FragmentSearchContactBinding
import com.aqa.proyectoclase.modelo.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SearchContactFragment : Fragment() {
    lateinit var binding : FragmentSearchContactBinding
    private lateinit var myRef: DatabaseReference
    private val database:FirebaseDatabase = Firebase.database
    private lateinit var contactosAdapter: CardContactosAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchContactBinding.inflate(inflater, container, false)
        var txtSearchUser : EditText = binding.txtName
        txtSearchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loadRecycler(binding.txtName.text.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loadRecycler(binding.txtName.text.toString())
//                Toast.makeText(context,"Test",Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(p0: Editable?) {
                loadRecycler(binding.txtName.text.toString())
            }
        })
        return binding.root
    }

    fun getResults(search : String) : ArrayList<User>{
        var users : ArrayList<User> = ArrayList()
        myRef = database.getReference("Users")
        myRef.orderByChild("username").startAt(search).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user:User? = snapshot.getValue<User>()
                if (user != null) {
                    users.add(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return users
    }

    fun loadRecycler(search: String){
        var users : ArrayList<User> = getResults(search)
        if(users.size >0){
            contactosAdapter = CardContactosAdapter(users,requireContext())
            binding.rclNameMatch.adapter = contactosAdapter
            binding.rclNameMatch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        }
    }



}