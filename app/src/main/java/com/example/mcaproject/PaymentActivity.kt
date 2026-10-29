
package com.example.mcaproject

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val equipmentName =
            intent.getStringExtra("equipment_name") ?: "Equipment"
        val emoji =
            intent.getStringExtra("equipment_emoji") ?: "🚜"
        val dailyPrice =
            intent.getIntExtra("equipment_price", 0)
        val startDate =
            intent.getStringExtra("start_date") ?: "Not selected"
        val endDate =
            intent.getStringExtra("end_date") ?: "Not selected"
        val days =
            intent.getIntExtra("rental_days", 0)
        val total =
            intent.getLongExtra("total_amount", 0L)

        findViewById<TextView>(R.id.paymentEquipmentName).text =
            "$emoji $equipmentName"

        findViewById<TextView>(R.id.paymentRentalDates).text =
            "Rental dates: $startDate – $endDate"

        findViewById<TextView>(R.id.paymentRentalDays).text =
            "Duration: $days day(s)"

        findViewById<TextView>(R.id.paymentDailyPrice).text =
            "Rate: ₹$dailyPrice/day"

        findViewById<TextView>(R.id.paymentTotalAmount).text =
            "₹$total"

        findViewById<TextView>(R.id.paymentBackButton)
            .setOnClickListener {
                finish()
            }

        val paymentMethods =
            findViewById<RadioGroup>(R.id.paymentMethodGroup)

        findViewById<Button>(R.id.payNowButton)
            .setOnClickListener {

                when (paymentMethods.checkedRadioButtonId) {
                    R.id.upiOption,
                    R.id.cardOption -> {
                        Toast.makeText(
                            this,
                            "Online payment is not configured yet. No money was charged.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    R.id.cashOption -> {
                        Toast.makeText(
                            this,
                            "Direct payment selected. Booking is not saved yet.",
                            Toast.LENGTH_LONG
                        ).show()
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
}
