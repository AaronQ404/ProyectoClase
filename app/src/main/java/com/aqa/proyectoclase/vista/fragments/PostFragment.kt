package com.aqa.proyectoclase.vista.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.MainController
import com.google.android.material.bottomnavigation.BottomNavigationView

class PostFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var btvNavigationView : BottomNavigationView = requireActivity().findViewById(R.id.bnvMenu)
        btvNavigationView.visibility = View.VISIBLE
//        btvNavigationView.selectedItemId =R.id.itmPosts
        return inflater.inflate(R.layout.fragment_post, container, false)
    }
}