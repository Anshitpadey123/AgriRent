
package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch

class SearchEquipment : AppCompatActivity() {

    private lateinit var adapter: EquipmentAdapter
    private lateinit var searchInput: EditText
    private lateinit var categoryGroup: RadioGroup
    private lateinit var priceSeekBar: SeekBar
    private lateinit var priceValue: TextView
    private lateinit var availableSwitch: MaterialSwitch
    private lateinit var resultCount: TextView
    private lateinit var emptyResults: TextView
    private lateinit var equipmentRecyclerView: RecyclerView

    private val allEquipment = listOf(
        Equipment(1, "Farm Tractor", "Tractor", 1200, true, "🚜", "Mathura"),
        Equipment(2, "Combine Harvester", "Harvester", 3500, true, "🌾", "Mathura"),
        Equipment(3, "Rotavator", "Implement", 800, false, "⚙️", "Kosi"),
        Equipment(4, "Mini Tractor", "Tractor", 900, true, "🚜", "Kosi"),
        Equipment(5, "Seed Drill", "Implement", 600, true, "🌱", "Mathura"),
        Equipment(6, "Power Tiller", "Implement", 700, true, "⚙️", "Agra"),
        Equipment(7, "Wheat Thresher", "Harvester", 1500, true, "🌾", "Kosi"),
        Equipment(8, "Heavy Duty Tractor", "Tractor", 2200, false, "🚜", "Agra"),
        Equipment(9, "Sprayer Machine", "Implement", 400, true, "💧", "Mathura"),
        Equipment(10, "Paddy Harvester", "Harvester", 3000, true, "🌾", "Kosi")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the exact name of your XML layout
        setContentView(R.layout.activity_search_equipement)

        searchInput = findViewById(R.id.searchEquipment)
        categoryGroup = findViewById(R.id.categoryRadioGroup)
        priceSeekBar = findViewById(R.id.priceSeekBar)
        priceValue = findViewById(R.id.priceValue)
        availableSwitch = findViewById(R.id.availableSwitch)
        resultCount = findViewById(R.id.resultCount)
        emptyResults = findViewById(R.id.emptyResults)
        equipmentRecyclerView = findViewById(R.id.equipmentRecyclerView)

        adapter = EquipmentAdapter(allEquipment)

        equipmentRecyclerView.layoutManager = LinearLayoutManager(this)
        equipmentRecyclerView.adapter = adapter
        equipmentRecyclerView.setHasFixedSize(true)

        // Search by equipment name, category, or location
        searchInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                filterEquipment()
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        // Filter by category
        categoryGroup.setOnCheckedChangeListener { _, _ ->
            filterEquipment()
        }

        // Filter by maximum daily rental price
        priceSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    priceValue.text = "₹$progress/day"

                    if (fromUser) {
                        filterEquipment()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            }
        )

        // Show available equipment only when switched on
        availableSwitch.setOnCheckedChangeListener { _, _ ->
            filterEquipment()
        }


        // Shared bottom navigation behavior
        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottomNav)

        // Highlight Search on the Search Equipment screen
        bottomNav.selectedItemId = R.id.nav_search

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_home -> {
                    val intent = Intent(
                        this@SearchEquipment,
                        Farmerhomescreen::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    true
                }

                R.id.nav_search -> {
                    // Already on the Search screen
                    true
                }

                R.id.nav_bookings -> {
                    // Connect your Bookings screen here later
                    true
                }

                R.id.nav_profile -> {
                    // Connect your Profile screen here later
                    true
                }

                else -> false
            }
        }


        // Show all equipment when the screen first opens
        filterEquipment()
    }

    private fun filterEquipment() {
        val query = searchInput.text.toString().trim()
        val maxPrice = priceSeekBar.progress

        val selectedCategory = when (categoryGroup.checkedRadioButtonId) {
            R.id.categoryTractor -> "Tractor"
            R.id.categoryHarvester -> "Harvester"
            R.id.categoryImplement -> "Implement"
            else -> "All"
        }

        val filteredList = allEquipment.filter { item ->

            val matchesSearch =
                item.name.contains(query, ignoreCase = true) ||
                        item.category.contains(query, ignoreCase = true) ||
                        item.location.contains(query, ignoreCase = true)

            val matchesCategory =
                selectedCategory == "All" ||
                        item.category == selectedCategory

            val matchesPrice = item.price <= maxPrice

            val matchesAvailability =
                !availableSwitch.isChecked || item.available

            matchesSearch &&
                    matchesCategory &&
                    matchesPrice &&
                    matchesAvailability
        }

        adapter.updateItems(filteredList)

        resultCount.text = "${filteredList.size} items"

        emptyResults.visibility =
            if (filteredList.isEmpty()) View.VISIBLE else View.GONE

        equipmentRecyclerView.visibility =
            if (filteredList.isEmpty()) View.GONE else View.VISIBLE
    }
}
