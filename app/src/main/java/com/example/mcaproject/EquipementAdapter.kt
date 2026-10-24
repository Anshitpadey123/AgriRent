package com.example.mcaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
            if (equipment.available) "View Details" else "Unavailable"

        holder.actionButton.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "${equipment.name} — ₹${equipment.price}/day",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Equipment>) {
        items = newItems
        notifyDataSetChanged()
    }
}
