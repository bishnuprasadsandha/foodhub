package com.example.thefoodhub.adaptar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefoodhub.DetailsActivity
import com.example.thefoodhub.databinding.ManuItemBinding
import com.example.thefoodhub.model.MenuItem

class MenuAdapter(
    private val menuItem: List<MenuItem>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.ManuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManuViewHolder {
        val binding = ManuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ManuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItem.size

    inner class ManuViewHolder(private val binding: ManuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItem[position]

            //a intent to open details activity and pass data
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemImage", menuItem.foodImage)
                putExtra("MenuItemDescription", menuItem.foodDescription)
                putExtra("MenuItemIngredients", menuItem.foodIngredient)
                putExtra("MenuItemPrice", menuItem.foodPrice)
            }
            //start the detail activity
            requireContext.startActivity(intent)
        }
// set data into recyclerview item name, price, image
        fun bind(position: Int) {
            val menuItem = menuItem[position]
            binding.apply {
                manuFoodName.text = menuItem.foodName
                manuPrice.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(manuImage)
            }
        }
    }
}
