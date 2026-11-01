package com.example.mcaproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
class Owner_HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_owner_home_screen)

        val cardMyEquipment = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardEquipment)
        cardMyEquipment.setOnClickListener {
            val intent = Intent(this, My_Equipment::class.java)
            startActivity(intent)
        }

        val cardBookingRequest = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardRequests)
        cardBookingRequest.setOnClickListener {
            val intent = Intent(this, Booking_Request::class.java)
            startActivity(intent)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_add -> {
                    startActivity(Intent(this, Add_Equipment::class.java))
                    true
                }
                R.id.nav_edit -> {
                    startActivity(Intent(this, rental_history::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, Owner_Profile::class.java))
                    true
                }

                else -> false
            }
        }

    }
}