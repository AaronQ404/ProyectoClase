package com.aqa.proyectoclase.vista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.databinding.ActivityMainBinding
import com.aqa.proyectoclase.vista.fragments.LoginFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragManager: FragmentManager  = supportFragmentManager
        val fragTransiction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragTransiction.add(R.id.frmLoginRegFrag, LoginFragment())
        fragTransiction.commit()
    }
}


