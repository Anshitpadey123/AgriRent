
package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EquipmentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment_details)

        val emoji = findViewById<TextView>(R.id.detailEmoji)
        val name = findViewById<TextView>(R.id.detailName)
        val category = findViewById<TextView>(R.id.detailCategory)
        val price = findViewById<TextView>(R.id.detailPrice)
        val availability = findViewById<TextView>(R.id.detailAvailability)
        val location = findViewById<TextView>(R.id.detailLocation)
        val description = findViewById<TextView>(R.id.detailDescription)
        val bookButton = findViewById<Button>(R.id.bookEquipmentButton)

        val equipmentId = intent.getIntExtra("equipment_id", -1)
        val equipmentName = intent.getStringExtra("equipment_name") ?: "Equipment"
        val equipmentCategory =
            intent.getStringExtra("equipment_category") ?: "Agricultural Equipment"
        val equipmentPrice = intent.getIntExtra("equipment_price", 0)
        val equipmentEmoji = intent.getStringExtra("equipment_emoji") ?: "🚜"
        val equipmentLocation =
            intent.getStringExtra("equipment_location") ?: "Location not specified"
        val isAvailable = intent.getBooleanExtra("equipment_available", true)

        emoji.text = equipmentEmoji
        name.text = equipmentName
        category.text = equipmentCategory
        price.text = "₹$equipmentPrice / day"
        location.text = "📍 Location: $equipmentLocation"

        availability.text = if (isAvailable) {
            "● Available for booking"
        } else {
            "● Currently unavailable"
        }

        availability.setTextColor(
            getColor(if (isAvailable) R.color.equipment_available
            else R.color.equipment_unavailable)
        )

        description.text =
            "$equipmentName is listed under $equipmentCategory. " +
                    "Contact the equipment owner for any additional information."

        bookButton.isEnabled = isAvailable
        bookButton.alpha = if (isAvailable) 1f else 0.5f

        bookButton.setOnClickListener {
            if (!isAvailable) {
                Toast.makeText(
                    this,
                    "This equipment is unavailable.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val bookingIntent = Intent(
                this@EquipmentDetailsActivity,
                BookEquipmentActivity::class.java
            ).apply {
                putExtra("equipment_id", equipmentId)
                putExtra("equipment_name", equipmentName)
                putExtra("equipment_category", equipmentCategory)
                putExtra("equipment_price", equipmentPrice)
                putExtra("equipment_emoji", equipmentEmoji)
                putExtra("equipment_location", equipmentLocation)
                putExtra("equipment_available", isAvailable)
            }

            startActivity(bookingIntent)
        }


        findViewById<TextView>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}
