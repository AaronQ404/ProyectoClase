package com.aqa.proyectoclase.vista

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.databinding.ActivityMainBinding
import com.aqa.proyectoclase.vista.fragments.ContactFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragManager: FragmentManager = supportFragmentManager
        val fragTransiction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragTransiction.add(binding.frmMainFrame.id,ContactFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }
}