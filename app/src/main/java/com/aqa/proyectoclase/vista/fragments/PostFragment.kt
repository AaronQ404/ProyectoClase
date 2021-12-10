package com.aqa.proyectoclase.vista.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.CardContactosAdapter
import com.aqa.proyectoclase.controlador.CardPostAdapter
import com.aqa.proyectoclase.controlador.MainController
import com.aqa.proyectoclase.databinding.FragmentPostBinding
import com.aqa.proyectoclase.modelo.Post
import com.aqa.proyectoclase.modelo.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PostFragment : Fragment() {
    lateinit var binding: FragmentPostBinding
    lateinit var rclPost: RecyclerView
    lateinit var posts : ArrayList<Post>
    val database : FirebaseDatabase = Firebase.database

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)
        MainController.Instance.changeBMenuVisibility(requireActivity(),true)
        posts = ArrayList()
        obtainPosts()
        var fbtnAddPost = binding.fbtnAddPost
        rclPost = binding.rclPosts
        fbtnAddPost.setOnClickListener{ view ->
            MainController.Instance.changeFragent(requireActivity(),R.id.frmMainFrame,CreatePostFragment())
        }
        return binding.root
    }

    fun loadRecycler(){
        if(posts.size > 0){
            binding.rclPosts.adapter = CardPostAdapter(posts,requireContext())
            binding.rclPosts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        }
    }

    fun obtainPosts() {
        val myRef = database.getReference().child("Posts")
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(a in snapshot.children){
                    val post: Post? = a.getValue<Post>()
                    if (post != null) {
                        posts.add(post)
                        loadRecycler()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}