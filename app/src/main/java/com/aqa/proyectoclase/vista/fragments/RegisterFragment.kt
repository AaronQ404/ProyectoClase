package com.aqa.proyectoclase.vista.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aqa.proyectoclase.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

import java.util.regex.Pattern


class RegisterFragment : Fragment() {
    val patronCorreo : Pattern = Patterns.EMAIL_ADDRESS
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val binding: FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater,container,false)
        binding.btnRegistrar.setOnClickListener {
            auth= Firebase.auth
            val correo = binding.txtCorreo.text.toString()
            val userName = binding.txtUsername.text.toString()
            val pass1 = binding.txtPass1.text.toString()
            val pass2 = binding.txtPass2.text.toString()
            val pass: String? = if(pass1.equals(pass2)) pass1 else null
            if(correo.trim().equals("")){
                Toast.makeText(this.context,"Debe de escribir un correo",Toast.LENGTH_SHORT).show()
            }else if (!patronCorreo.matcher(correo).matches()){
                Toast.makeText(this.context,"Debe introducir un correo valido",Toast.LENGTH_SHORT).show()
            }else if(userName.trim().equals("")){
                Toast.makeText(this.context,"Debe de introducir un nombre de usuario valido",Toast.LENGTH_SHORT).show()
            }else if(pass?.trim().equals("")){
                Toast.makeText(this.context,"Debe de introducir una contraseña",Toast.LENGTH_SHORT).show()
            }else if(pass == null ) {
                Toast.makeText(this.context,"Las contraseñas deben de coincidir",Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener{
                    val user = auth?.currentUser
                    val profileCreateUp = userProfileChangeRequest {
                        displayName = userName
                    }
                    user!!.updateProfile(profileCreateUp).addOnCompleteListener {
                        Toast.makeText(this.context,"Usuario creado con exito",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return binding.root
    }



}