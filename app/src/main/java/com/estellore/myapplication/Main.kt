package com.estellore.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Main : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Apply window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up bottom navigation view to handle fragment transactions
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)

        // Display ListFragment by default when the app opens
        if (savedInstanceState == null) {
            // Ensure the ListFragment is shown initially
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()

            // Set the selected item to 'List' in the bottom navigation
            bottomNavigationView.selectedItemId = R.id.nav_list
        }

        // Set up bottom navigation view item selection listener
        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.nav_calculator -> {
                    fragmentTransaction.replace(R.id.fragment_container, CalculatorFragment())
                }
                R.id.nav_list -> {
                    fragmentTransaction.replace(R.id.fragment_container, ListFragment())
                }
                R.id.nav_profile -> {
                    fragmentTransaction.replace(R.id.fragment_container, ProfileFragment())
                }
            }
            fragmentTransaction.addToBackStack(null) // Allows back navigation
            fragmentTransaction.commit()  // Execute the transaction
            true
        }
    }
}
