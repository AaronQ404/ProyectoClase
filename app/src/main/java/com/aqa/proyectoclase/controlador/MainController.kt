package com.aqa.proyectoclase.controlador

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.vista.fragments.ChatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainController {
    object Instance{

        init {

            }
        fun changeFragent(activity : FragmentActivity?,idFrameLayout : Int, fragment : Fragment, addTBStack : String? = null, hideBMenu: Boolean = false) {

            if (activity != null) {
                var btvNavigationView : BottomNavigationView = activity.findViewById(R.id.bnvMenu)
                activity.supportFragmentManager.beginTransaction().replace(
                        idFrameLayout,
                        fragment).addToBackStack(addTBStack).commit()
                if(hideBMenu){
                    btvNavigationView.visibility = View.INVISIBLE
                }else{
                    btvNavigationView.visibility = View.VISIBLE
                }
            }
        }

        fun changeBMenuVisibility(activity : FragmentActivity?,setVisible : Boolean){
            if(activity!=null){
                var btvNavigationView : BottomNavigationView = activity.findViewById(R.id.bnvMenu)
                if(setVisible){
                    btvNavigationView.visibility = View.VISIBLE
                }else{
                    btvNavigationView.visibility = View.INVISIBLE
                }
            }
        }
    }



}