package com.aqa.proyectoclase.vista.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aqa.proyectoclase.databinding.FragmentContactBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class ContactFragment : Fragment() {
    private lateinit var auth:FirebaseAuth
    private lateinit var myRef:DatabaseReference
    private val contexto = context
    lateinit var  userId : String
    private lateinit var user :FirebaseUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        auth = Firebase.auth
        val database = Firebase.database
        val myRef = database.getReference("Contactos").child(auth.currentUser?.uid.toString()).child("User1")
        user = auth.currentUser!!
        val provId = user.providerData.toString()
        // Inflate the layout for this fragment
        val binding: FragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false)

        binding.txtUser.text = user?.displayName.toString()

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<String>()
                binding.txtTest.text = value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

//        getData()
        if(user.isAnonymous){
            userId="anonimo"
        }else if(user.isEmailVerified){
            userId="email no verificado"
        }else{
            userId="nada"
        }
        binding.txtTest.text = userId
        return binding.root
    }

    private fun getData(){
        val misContactos:DatabaseReference = myRef.child("test")
        try {
            val userListener = object : ValueEventListener {
                val algo = "asdf"

                //TODO no se ejecuta este codigo
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userId=dataSnapshot.getValue().toString()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // handle error


                    Toast.makeText(context,"Error al consultar",Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    userId="Error"
                }
            }
            misContactos.addValueEventListener(userListener)

        }catch(ex:Exception){
            Log.w(TAG, "loadPost:onCancelled\n ${ex.message}\n ${ex.stackTrace}", ex)
        }


    }

}





