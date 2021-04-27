package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.instagramclone.Fragments.ActivityFragment
import com.example.instagramclone.Fragments.HomeFragment
import com.example.instagramclone.Fragments.ProfileFragment
import com.example.instagramclone.Fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView.setOnNavigationItemSelectedListener{menuItem ->
            when (menuItem.itemId){
                R.id.home -> {
                    fragment = HomeFragment()
                }
                R.id.search -> {
                    fragment = SearchFragment()
                }
                R.id.add -> {
                    fragment = null
                }
                R.id.activity -> {
                    fragment = ActivityFragment()
                }
                R.id.profile -> {
                    fragment = ProfileFragment()
                }
            }
            if (fragment != null){
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment!!).commit()
            }
            return@setOnNavigationItemSelectedListener true
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()

    }
}