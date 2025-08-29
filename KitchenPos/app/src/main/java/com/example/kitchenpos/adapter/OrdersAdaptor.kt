package com.example.kitchenpos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenpos.databinding.ItemOrderBinding
import com.example.kitchenpos.model.OrderResponse

class OrdersAdapter(private val orders: List<OrderResponse>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.txtOrderId.text = "Order #${order.order_id}"
        holder.binding.txtStatus.text = "Status: ${order.status}"
        holder.binding.txtTotal.text = "Total: ₹${order.total_price}"

        // Show items
        val itemsText = order.items.joinToString("\n") {
            "${it.food_name} x${it.quantity} = ₹${it.price * it.quantity}"
        }
        holder.binding.txtItems.text = itemsText
    }

    override fun getItemCount() = orders.size
}
