package com.aqa.proyectoclase.vista.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.databinding.FragmentLoginBinding
import com.aqa.proyectoclase.modelo.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class LoginFragment : Fragment() {
    private lateinit var database: DatabaseReference
    lateinit var usuario: User
    val patronCorreo: Pattern = Patterns.EMAIL_ADDRESS
    val auth = Firebase.auth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        database = Firebase.database.reference
        // Inflate the layout for this fragment
//        if(auth.currentUser!=null){
//            Firebase.auth.signOut()
//        }

        val binding: FragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false)
        binding.btnLogin.setOnClickListener {
            lateinit var nick: String
            val username = binding.txtUsuario.text.toString()
            val pass = binding.pwdPass.text.toString()
           if(username.trim().equals("")){ Toast.makeText(this.context,"Debe de introducir el correo",Toast.LENGTH_SHORT).show() }
            else if(!patronCorreo.matcher(username.trim()).matches()){ Toast.makeText(this.context,"Correo erroneo",Toast.LENGTH_SHORT).show()}
            else if(pass.trim().equals("")){Toast.makeText(this.context,"Debe de introducir la contraseña",Toast.LENGTH_SHORT).show()}
            else if(pass.length < 8){Toast.makeText(this.context, "La contraseña debe de tener más de 8 caracteres.",Toast.LENGTH_SHORT).show()}
            else{

               auth.signInWithEmailAndPassword(username, pass)
                       .addOnCompleteListener(OnCompleteListener  { task ->
                           if (task.isSuccessful) {
                               // Sign in success, update UI with the signed-in user's information
                               val user: FirebaseUser? = auth.currentUser
                                nick = user?.displayName.toString()
                               Toast.makeText(this.context,"Logueado: ${nick}",Toast.LENGTH_SHORT).show()
                               val bundle: Bundle = Bundle()
                               bundle.putParcelable("user",user)
                               val userFragment:ContactFragment = ContactFragment()
                               activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frmLoginRegFrag,userFragment)
                                   ?.addToBackStack(null)?.commit()
                           }
                           else {
                               // If sign in fails, display a message to the user.
                               Toast.makeText(this.context,"Este usuario no existe.",Toast.LENGTH_SHORT).show()
                           }
                       })
            }
        }

        binding.btnRegister.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frmLoginRegFrag,RegisterFragment())
                    ?.addToBackStack(null)?.commit()
        }
        return binding.root
    }



}