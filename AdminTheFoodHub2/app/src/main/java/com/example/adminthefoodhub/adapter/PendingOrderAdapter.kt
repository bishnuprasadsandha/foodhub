package com.example.adminthefoodhub.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Key
import com.example.adminthefoodhub.databinding.PendingOrdersItemBinding

class PendingOrderAdapter(
    private val context: Context,
    private val customerName: MutableList<String>,
    private val quantity: MutableList<String>,
    private val foodImage: MutableList<String>,
    private val itemClicked : OnItemClicked,
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked{
        fun onItemClickListener(position: Int)
        fun onItemAcceptClickListener(position: Int)
        fun onItemDispatchClickListener(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =
            PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size

    inner class PendingOrderViewHolder(private val binding: PendingOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var isAccepted = false
        fun bind(position: Int) {
            binding.customerName.text = customerName[position]
            binding.pendingOrderQuantity.text = quantity[position]
            var uriString = foodImage[position]
            var uri = Uri.parse(uriString)
            Glide.with(context).load(uri).into(binding.orderFoodImage)

            binding.orderAcceptButton.apply {
                if (!isAccepted) {
                    text = "Accept"
                } else {
                    text = "Dispatch"
                }
                setOnClickListener {
                    if (!isAccepted) {
                        text = "Dispatch"
                        isAccepted = true
                        showToast("Order is Accepted")
                        itemClicked.onItemAcceptClickListener(position)
                    } else {
                        customerName.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        showToast("Order Is Dispatch")
                        itemClicked.onItemDispatchClickListener(position)
                    }
                }
            }
            itemView.setOnClickListener {
                itemClicked.onItemClickListener(position)
            }
        }
        private fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
