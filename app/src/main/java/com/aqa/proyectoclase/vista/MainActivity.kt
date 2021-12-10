package com.aqa.proyectoclase.vista

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.controlador.MainController
import com.aqa.proyectoclase.databinding.ActivityMainBinding
import com.aqa.proyectoclase.vista.fragments.ChatFragment
import com.aqa.proyectoclase.vista.fragments.ContactFragment
import com.aqa.proyectoclase.vista.fragments.PostFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding;
    lateinit var btvMenu: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btvMenu = binding.bnvMenu
        MainController.Instance.changeFragent(this,binding.frmMainFrame.id,PostFragment())
        supportFragmentManager
        btvMenu.selectedItemId = R.id.itmPosts
        btvMenu.setOnNavigationItemSelectedListener{
            menuItem ->
            when(menuItem.itemId) {
                R.id.itmPosts -> {
                    MainController.Instance.changeFragent(this,binding.frmMainFrame.id,PostFragment())
                    true
                }
                R.id.itmChats -> {
                    MainController.Instance.changeFragent(this,binding.frmMainFrame.id,ContactFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

}