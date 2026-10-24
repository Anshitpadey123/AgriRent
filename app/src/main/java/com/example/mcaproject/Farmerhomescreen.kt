
package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class Farmerhomescreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmerhomescreen)

        val searchCard =
            findViewById<MaterialCardView>(R.id.searchCard)

        val bookingsCard =
            findViewById<MaterialCardView>(R.id.bookingsCard)

        val equipmentCard =
            findViewById<MaterialCardView>(R.id.tractorEquipmentCard)

        val viewAll =
            findViewById<TextView>(R.id.viewAllEquipment)

        // Open Search Equipment from the search card
        searchCard.setOnClickListener {
            openSearchEquipment()
        }

        bookingsCard.setOnClickListener {
            showMessage("My Bookings screen will open here")
        }

        equipmentCard.setOnClickListener {
            showMessage("Equipment Details screen will open here")
        }

        viewAll.setOnClickListener {
            openSearchEquipment()
        }

        // Bottom navigation
        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_home -> true

                R.id.nav_search -> {
                    openSearchEquipment()
                    true
                }

                R.id.nav_bookings -> {
                    showMessage("My Bookings screen will open here")
                    true
                }

                R.id.nav_profile -> {
                    showMessage("Farmer Profile screen will open here")
                    true
                }

                else -> false
            }
        }
    }

    private fun openSearchEquipment() {

            intent=Intent(this@Farmerhomescreen, SearchEquipment::class.java)
        startActivity(intent)
        finish()


    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
