package com.example.kitchenpos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kitchenpos.databinding.ItemFoodBinding
import com.example.kitchenpos.model.Food

class FoodAdapter(
    private val foods: List<Food>,
    private val onAddClick: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]

        // Set name and price
        holder.binding.txtName.text = food.name
        holder.binding.txtPrice.text = "₹${food.price}"

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(food.imageRes)
            .centerCrop()
            .into(holder.binding.imgFood)

        // Handle Add button click
        holder.binding.btnAdd.setOnClickListener {
            onAddClick(food)
        }
    }

    override fun getItemCount(): Int = foods.size
}
