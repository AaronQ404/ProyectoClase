package com.aqa.proyectoclase.vista.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.MainController
import com.aqa.proyectoclase.databinding.FragmentCreatePostBinding
import com.aqa.proyectoclase.modelo.Post
import com.aqa.proyectoclase.vista.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CreatePostFragment : Fragment() {
    lateinit var binding: FragmentCreatePostBinding
    lateinit var txtTitulo : EditText
    lateinit var txtContenido : EditText
    val database: FirebaseDatabase = Firebase.database
    private val user: FirebaseUser = Firebase.auth.currentUser!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainController.Instance.changeBMenuVisibility(requireActivity(),false)
        binding = FragmentCreatePostBinding.inflate(inflater,container, false)
        val btnPublicar: Button = binding.btnPostIt
        txtTitulo = binding.etxtTitle
        txtContenido = binding.etxtContent

        btnPublicar.setOnClickListener(View.OnClickListener {
            var titulo: String = txtTitulo.text.toString()
            var contenido: String = txtContenido.text.toString()
            var imagen:String

            if(!checkText(titulo)||!checkText(contenido)){
                Toast.makeText(context,"Debe de rellenar todos los campos",Toast.LENGTH_SHORT).show()
            }else{
                titulo
                database.getReference("Posts").child(System.currentTimeMillis().toString() + user.uid).setValue(
                    Post(user.uid,titulo,contenido)
                )
                Toast.makeText(context,"Post creado",Toast.LENGTH_SHORT).show()
                MainController.Instance.changeFragent(requireActivity(),R.id.frmMainFrame,PostFragment())
            }
        })
        return binding.root
    }


    fun checkText(text:String?): Boolean{
        val isEmpty = if(text == null || text.trim().equals("")) false else true
        return isEmpty
    }

}