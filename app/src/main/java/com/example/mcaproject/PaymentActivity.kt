package com.example.mcaproject

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val equipmentName =
            intent.getStringExtra("equipment_name") ?: "Equipment"

        val equipmentEmoji =
            intent.getStringExtra("equipment_emoji") ?: "🚜"

        val dailyPrice =
            intent.getIntExtra("equipment_price", 0)

        val startDate =
            intent.getStringExtra("start_date") ?: "Not selected"

        val endDate =
            intent.getStringExtra("end_date") ?: "Not selected"

        val rentalDays =
            intent.getIntExtra("rental_days", 0)

        val totalAmount =
            intent.getLongExtra("total_amount", 0L)

        findViewById<TextView>(R.id.paymentEquipmentName).text =
            "$equipmentEmoji $equipmentName"

        findViewById<TextView>(R.id.paymentRentalDates).text =
            "Rental dates: $startDate – $endDate"

        findViewById<TextView>(R.id.paymentRentalDays).text =
            "Duration: $rentalDays day(s)"

        findViewById<TextView>(R.id.paymentDailyPrice).text =
            "Rate: ₹$dailyPrice/day"

        findViewById<TextView>(R.id.paymentTotalAmount).text =
            "₹$totalAmount"

        findViewById<TextView>(R.id.paymentBackButton)
            .setOnClickListener {
                finish()
            }

        val paymentMethodGroup =
            findViewById<RadioGroup>(R.id.paymentMethodGroup)

        findViewById<Button>(R.id.payNowButton)
            .setOnClickListener {

                when (paymentMethodGroup.checkedRadioButtonId) {

                    R.id.upiOption -> {
                        Toast.makeText(
                            this,
                            "UPI payments are not enabled yet. No money was charged.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    R.id.cardOption -> {
                        Toast.makeText(
                            this,
                            "Card payments are not enabled yet. No money was charged.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    R.id.cashOption -> {
                        if (saveBookingRequest(
                                equipmentName = equipmentName,
                                emoji = equipmentEmoji,
                                startDate = startDate,
                                endDate = endDate,
                                rentalDays = rentalDays,
                                totalAmount = totalAmount
                            )
                        ) {
                            Toast.makeText(
                                this,
                                "Booking request saved. Payment is pending.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Could not save booking. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    else -> {
                        Toast.makeText(
                            this,
                            "Please select a payment method.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun saveBookingRequest(
        equipmentName: String,
        emoji: String,
        startDate: String,
        endDate: String,
        rentalDays: Int,
        totalAmount: Long
    ): Boolean {
        return try {
            val preferences = getSharedPreferences(
                "AgriRentBookings",
                MODE_PRIVATE
            )

            val bookings = JSONArray(
                preferences.getString("bookings", "[]")
            )

            val booking = JSONObject().apply {
                put("equipmentName", equipmentName)
                put("emoji", emoji)
                put("startDate", startDate)
                put("endDate", endDate)
                put("rentalDays", rentalDays)
                put("totalAmount", totalAmount)
                put("status", "Pending owner confirmation")
                put("paymentMethod", "Direct payment")
                put("paymentStatus", "Pending")
                put("createdAt", System.currentTimeMillis())
            }

            bookings.put(booking)

            // commit() returns true only when the write succeeds.
            preferences.edit()
                .putString("bookings", bookings.toString())
                .commit()

        } catch (e: Exception) {
            false
        }
    }
}
