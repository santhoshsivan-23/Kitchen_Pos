package com.example.kitchenpos_2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenpos_2.databinding.ItemKitchenOrderItemBinding
import com.example.kitchenpos_2.model.OrderItemResponse

class KitchenOrderItemsAdapter(
    private val items: List<OrderItemResponse>
) : RecyclerView.Adapter<KitchenOrderItemsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemKitchenOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemKitchenOrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.txtFoodName.text = item.food_name
        holder.binding.txtQuantity.text = "Qty: ${item.quantity}"
        holder.binding.txtPrice.text = "₹${item.price}"
    }

    override fun getItemCount() = items.size
}
