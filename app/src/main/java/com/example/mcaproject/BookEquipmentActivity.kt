package com.example.mcaproject

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class BookEquipmentActivity : AppCompatActivity() {

    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button
    private lateinit var rentalDaysText: TextView
    private lateinit var totalPriceText: TextView

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    private var equipmentId = -1
    private var equipmentName = "Equipment"
    private var equipmentCategory = "Equipment"
    private var equipmentEmoji = "🚜"
    private var equipmentLocation = "Location not specified"
    private var dailyPrice = 0
    private var equipmentAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_equipment)

        // Receive equipment information
        equipmentId = intent.getIntExtra("equipment_id", -1)
        equipmentName =
            intent.getStringExtra("equipment_name") ?: "Equipment"
        equipmentCategory =
            intent.getStringExtra("equipment_category") ?: "Equipment"
        equipmentEmoji =
            intent.getStringExtra("equipment_emoji") ?: "🚜"
        equipmentLocation =
            intent.getStringExtra("equipment_location")
                ?: "Location not specified"
        dailyPrice = intent.getIntExtra("equipment_price", 0)
        equipmentAvailable =
            intent.getBooleanExtra("equipment_available", false)

        // Display equipment details
        findViewById<TextView>(R.id.bookingEquipmentEmoji).text =
            equipmentEmoji

        findViewById<TextView>(R.id.bookingEquipmentName).text =
            equipmentName

        findViewById<TextView>(R.id.bookingEquipmentCategory).text =
            "$equipmentCategory • $equipmentLocation"

        findViewById<TextView>(R.id.bookingDailyPrice).text =
            "₹$dailyPrice / day"

        startDateButton = findViewById(R.id.startDateButton)
        endDateButton = findViewById(R.id.endDateButton)
        rentalDaysText = findViewById(R.id.rentalDaysText)
        totalPriceText = findViewById(R.id.bookingTotalPrice)

        startDateButton.setOnClickListener {
            showDatePicker(isStartDate = true)
        }

        endDateButton.setOnClickListener {
            if (startDate == null) {
                Toast.makeText(
                    this,
                    "Please select the start date first.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showDatePicker(isStartDate = false)
            }
        }

        findViewById<TextView>(R.id.bookingBackButton)
            .setOnClickListener {
                finish()
            }

        findViewById<Button>(R.id.confirmBookingButton)
            .setOnClickListener {
                confirmBooking()
            }

        updateTotal()
    }

    private fun showDatePicker(isStartDate: Boolean) {
        val today = Calendar.getInstance()

        val initialDate = if (isStartDate) {
            startDate ?: today
        } else {
            endDate ?: startDate ?: today
        }

        val picker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->

                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth, 0, 0, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                if (isStartDate) {
                    startDate = selectedDate

                    startDateButton.text =
                        "Start: ${formatDate(selectedDate)}"

                    // Reset the end date if it is now invalid.
                    if (endDate != null &&
                        endDate!!.before(selectedDate)
                    ) {
                        endDate = null
                        endDateButton.text = "Select End Date"
                    }

                } else {
                    val selectedStart = startDate

                    if (selectedStart == null ||
                        selectedDate.before(selectedStart)
                    ) {
                        Toast.makeText(
                            this,
                            "End date cannot be before start date.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@DatePickerDialog
                    }

                    endDate = selectedDate

                    endDateButton.text =
                        "End: ${formatDate(selectedDate)}"
                }

                updateTotal()
            },
            initialDate.get(Calendar.YEAR),
            initialDate.get(Calendar.MONTH),
            initialDate.get(Calendar.DAY_OF_MONTH)
        )

        picker.datePicker.minDate =
            if (!isStartDate && startDate != null) {
                startDate!!.timeInMillis
            } else {
                today.timeInMillis
            }

        picker.show()
    }

    private fun formatDate(date: Calendar): String {
        return "%02d/%02d/%04d".format(
            date.get(Calendar.DAY_OF_MONTH),
            date.get(Calendar.MONTH) + 1,
            date.get(Calendar.YEAR)
        )
    }

    private fun getRentalDays(): Int {
        val start = startDate ?: return 0
        val end = endDate ?: return 0

        val difference = end.timeInMillis - start.timeInMillis
        val oneDay = 24L * 60L * 60L * 1000L

        // Both the start and end dates are included.
        return (difference / oneDay).toInt() + 1
    }

    private fun updateTotal() {
        val days = getRentalDays()

        rentalDaysText.text = "Rental duration: $days day(s)"

        val total = dailyPrice.toLong() * days
        totalPriceText.text = "₹$total"
    }

    private fun confirmBooking() {
        if (!equipmentAvailable) {
            Toast.makeText(
                this,
                "This equipment is currently unavailable.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val farmerNameInput =
            findViewById<EditText>(R.id.farmerNameInput)

        val farmerPhoneInput =
            findViewById<EditText>(R.id.farmerPhoneInput)

        val farmerName = farmerNameInput.text.toString().trim()
        val farmerPhone = farmerPhoneInput.text.toString().trim()

        if (farmerName.isEmpty()) {
            farmerNameInput.error = "Enter your full name"
            farmerNameInput.requestFocus()
            return
        }

        if (!farmerPhone.matches(Regex("[0-9]{10}"))) {
            farmerPhoneInput.error =
                "Enter a valid 10-digit phone number"
            farmerPhoneInput.requestFocus()
            return
        }

        if (startDate == null || endDate == null) {
            Toast.makeText(
                this,
                "Please select both rental dates.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val days = getRentalDays()

        if (days <= 0) {
            Toast.makeText(
                this,
                "Please select a valid rental period.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val total = dailyPrice.toLong() * days

        // Open the Payment Screen with booking information.
        val paymentIntent = Intent(
            this@BookEquipmentActivity,
            PaymentActivity::class.java
        ).apply {
            putExtra("equipment_id", equipmentId)
            putExtra("equipment_name", equipmentName)
            putExtra("equipment_category", equipmentCategory)
            putExtra("equipment_emoji", equipmentEmoji)
            putExtra("equipment_location", equipmentLocation)
            putExtra("equipment_price", dailyPrice)
            putExtra("equipment_available", equipmentAvailable)

            putExtra("start_date", formatDate(startDate!!))
            putExtra("end_date", formatDate(endDate!!))
            putExtra("rental_days", days)
            putExtra("total_amount", total)

            putExtra("farmer_name", farmerName)
            putExtra("farmer_phone", farmerPhone)
        }

        startActivity(paymentIntent)
    }
}
