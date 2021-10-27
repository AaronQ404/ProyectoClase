package com.aqa.proyectoclase.vista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.MainActivityController
import com.aqa.proyectoclase.modelo.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {
    val mainController = MainActivityController()
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnLoging : Button = this.findViewById(R.id.btnLogin)
        val txtUser : EditText = this.findViewById(R.id.txt_Usuario)
        val pwdPass : EditText = this.findViewById(R.id.pwd_pass)
        btnLoging.setOnClickListener {
            database = Firebase.database.reference
            val user: String;
            val pass: String;
            user = txtUser.text.toString()
            pass = pwdPass.text.toString()

            val auth = Firebase.auth
            auth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(OnCompleteListener  { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        var nick : String = "."

                        val postListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // Get Post object and use the values to update the UI
                                val post = dataSnapshot.getValue<User>()
                                // ...
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Getting Post failed, log a message
//                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                            }
                        }
//                        postReference.addValueEventListener(postListener)
//                        database.child("Users").child(user?.uid.toString()).child("nick")
//                            .get().addOnSuccessListener {
//                                nick = it.value.toString()
//                        }

                        val userData = database.child("Users").child(user?.uid.toString()).child("Nick")
                        Toast.makeText(this,"logueado ${nick}",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this,"no logueado",Toast.LENGTH_SHORT).show()
                    }
                })


        }

    }
}