package com.xpressfixing

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.xpressfixing.databinding.ActivityLoginBinding
import com.xpressfixing.databinding.ActivityMainBinding
import com.xpressfixing.fragment.AccountFragment
import com.xpressfixing.fragment.CategoriesFragment
import com.xpressfixing.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var databpase: FirebaseDatabase? = null

    val MYPREFRENCES = "Preferences"
    val NAME = "username"
    val KEY_DARKMODE = "isDarkMode"
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

      bottomNavigation()
    }

    fun bottomNavigation(){
        val homeFragment = HomeFragment()
        val categoriesFragment = CategoriesFragment()
        val accountFragment = AccountFragment()

        makeFragment(homeFragment)

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.main_menu_home -> {
                    makeFragment(homeFragment) }

                R.id.main_menu_categories -> {
                    makeFragment(categoriesFragment) }

                R.id.main_menu_account -> {
                    makeFragment(accountFragment) }
            }
            true
        }

        binding.bottomNav.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.main_menu_home -> {
                    makeFragment(homeFragment) }

                R.id.main_menu_categories -> {
                    makeFragment(categoriesFragment) }

                R.id.main_menu_account -> {
                    makeFragment(accountFragment) }
            }
            true
        }
    }

    private fun makeFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
}