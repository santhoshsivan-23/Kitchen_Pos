package com.example.kitchenpos_2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenpos_2.databinding.ItemKitchenOrderBinding
import com.example.kitchenpos_2.model.OrderResponse

class KitchenOrdersAdapter(
    private val orders: List<OrderResponse>,
    private val onOrderClick: (OrderResponse) -> Unit
) : RecyclerView.Adapter<KitchenOrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemKitchenOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderResponse) {
            binding.txtOrderId.text = "Order #${order.order_id}"
            binding.txtTotal.text = "Total: ₹${order.total_price}"
            binding.txtStatus.text = "Status: ${order.status}"

            binding.root.setOnClickListener {
                onOrderClick(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemKitchenOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size
}
