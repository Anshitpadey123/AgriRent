package com.example.mcaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class OwnerEquipmentAdapter(
    private val equipmentList: ArrayList<OwnerEquipment>
) : RecyclerView.Adapter<OwnerEquipmentAdapter.EquipmentViewHolder>() {

    class EquipmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgEquipment: ImageView = itemView.findViewById(R.id.imgEquipment)

        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
        val txtEquipmentName: TextView = itemView.findViewById(R.id.txtEquipmentName)
        val txtBrand: TextView = itemView.findViewById(R.id.txtBrand)
        val txtModel: TextView = itemView.findViewById(R.id.txtModel)
        val txtRent: TextView = itemView.findViewById(R.id.txtRent)
        val txtLocation: TextView = itemView.findViewById(R.id.txtLocation)
        val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
        val txtStatus: TextView = itemView.findViewById(R.id.txtStatus)

        val btnEdit: MaterialButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_equipment, parent, false)

        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {

        val equipment = equipmentList[position]

        holder.txtCategory.text = equipment.category
        holder.txtEquipmentName.text = equipment.equipmentName
        holder.txtBrand.text = "Brand : ${equipment.brand}"
        holder.txtModel.text = "Model : ${equipment.model}"
        holder.txtRent.text = "₹${equipment.rentPerDay} / Day"
        holder.txtLocation.text = "📍 ${equipment.location}"
        holder.txtDescription.text = equipment.description
        holder.txtStatus.text = equipment.status

        if (equipment.status == "Available") {
            holder.txtStatus.setBackgroundResource(R.drawable.status_available_bg)
        } else {
            holder.txtStatus.setBackgroundResource(R.drawable.status_rented_bg)
        }

        holder.btnEdit.setOnClickListener {
            // Edit Equipment
        }

        holder.btnDelete.setOnClickListener {
            // Delete Equipment
        }
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }
}