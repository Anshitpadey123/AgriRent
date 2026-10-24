
package com.example.mcaproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class EquipmentAdapter(
    private var items: List<Equipment>
) : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    class EquipmentViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val emoji: TextView =
            itemView.findViewById(R.id.equipmentEmoji)

        val name: TextView =
            itemView.findViewById(R.id.equipmentName)

        val category: TextView =
            itemView.findViewById(R.id.equipmentCategory)

        val availability: TextView =
            itemView.findViewById(R.id.equipmentAvailability)

        val price: TextView =
            itemView.findViewById(R.id.equipmentPrice)

        val actionButton: MaterialButton =
            itemView.findViewById(R.id.equipmentActionButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipmentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_equipement, parent, false)

        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EquipmentViewHolder,
        position: Int
    ) {
        val equipment = items[position]

        holder.emoji.text = equipment.emoji
        holder.name.text = equipment.name
        holder.category.text =
            "${equipment.category} • ${equipment.location}"

        holder.price.text = "₹${equipment.price}/day"

        holder.availability.text =
            if (equipment.available) {
                "Available"
            } else {
                "Currently unavailable"
            }

        val availabilityColor = holder.itemView.context.getColor(
            if (equipment.available) {
                R.color.equipment_available
            } else {
                R.color.equipment_unavailable
            }
        )

        holder.availability.setTextColor(availabilityColor)

        holder.actionButton.isEnabled = equipment.available
        holder.actionButton.text =
            if (equipment.available) "View Details"
            else "Unavailable"

        holder.actionButton.setOnClickListener {
            if (equipment.available) {
                openEquipmentDetails(holder, equipment)
            }
        }

        // Tapping the equipment card also opens its details.
        holder.itemView.setOnClickListener {
            if (equipment.available) {
                openEquipmentDetails(holder, equipment)
            }
        }
    }

    private fun openEquipmentDetails(
        holder: EquipmentViewHolder,
        equipment: Equipment
    ) {
        val intent = Intent(
            holder.itemView.context,
            EquipmentDetailsActivity::class.java
        ).apply {
            putExtra("equipment_id", equipment.id)
            putExtra("equipment_name", equipment.name)
            putExtra("equipment_category", equipment.category)
            putExtra("equipment_price", equipment.price)
            putExtra("equipment_emoji", equipment.emoji)
            putExtra("equipment_location", equipment.location)
            putExtra("equipment_available", equipment.available)
        }

        holder.itemView.context.startActivity(intent)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Equipment>) {
        items = newItems
        notifyDataSetChanged()
    }
}
