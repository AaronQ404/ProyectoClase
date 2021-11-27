package com.aqa.proyectoclase.vista

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.databinding.ActivityMainBinding
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
        btvMenu = binding.bnvMenu
        setContentView(binding.root)
        openFragment(PostFragment())

        btvMenu.setOnNavigationItemSelectedListener{
            menuItem ->
            when(menuItem.itemId) {
                R.id.itmPosts -> {
                    openFragment(PostFragment())
                    true
                }
                R.id.itmChats -> {
                    openFragment(ContactFragment())
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
    /**
     * Método encargado de gestionar los diferentes fragmentos de la aplicacion
     * */
    fun openFragment(fragment : Fragment){
        val fragTransiction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragTransiction.replace(binding.frmMainFrame.id,fragment)
            .commit()

    }

}