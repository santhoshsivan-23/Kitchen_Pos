package com.example.kitchenpos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenpos.databinding.ItemCartBinding
import com.example.kitchenpos.model.Food

class CartAdapter(
    private val cartItems: MutableList<Pair<Food, Int>>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (food, quantity) = cartItems[position]
        holder.binding.txtName.text = food.name
        holder.binding.txtPrice.text = "₹${food.price}"
        holder.binding.txtQuantity.text = quantity.toString()

        // Increase qty
        holder.binding.btnPlus.setOnClickListener {
            updateQuantity(position, quantity + 1)
        }

        // Decrease qty
        holder.binding.btnMinus.setOnClickListener {
            if (quantity > 1) updateQuantity(position, quantity - 1)
        }
    }

    override fun getItemCount() = cartItems.size

    private fun updateQuantity(position: Int, newQuantity: Int) {
        val (food, _) = cartItems[position]
        cartItems[position] = Pair(food, newQuantity)
        notifyItemChanged(position)
        onQuantityChanged()
    }

    fun getCartItems(): List<Pair<Food, Int>> = cartItems
}
