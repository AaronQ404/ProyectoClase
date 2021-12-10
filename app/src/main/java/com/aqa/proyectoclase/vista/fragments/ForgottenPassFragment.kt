package com.aqa.proyectoclase.vista.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.databinding.FragmentForgottenPassBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgottenPassFragment : Fragment() {
    lateinit var binding : FragmentForgottenPassBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding =  FragmentForgottenPassBinding.inflate(inflater, container, false)
        binding.btnRecoverPass.setOnClickListener{
            val emailAddress = binding.etxtEmailRPass.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Snackbar.make(binding.root, "Se ha enviado un correo para recuperar la contraseña",Snackbar.LENGTH_SHORT).show()
                        }else{
                           Snackbar.make(binding.root, "Este correo no existe",Snackbar.LENGTH_SHORT).show()
                        }
                    }
        }
        return binding.root
    }
}