package com.example.kitchenpos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kitchenpos.adapter.FoodAdapter
import com.example.kitchenpos.databinding.FragmentHomeBinding
import com.example.kitchenpos.model.CartManager
import com.example.kitchenpos.model.foods

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = FoodAdapter(foods) { food ->
            CartManager.addToCart(food)
            Toast.makeText(requireContext(), "${food.name} added to cart", Toast.LENGTH_SHORT).show()
        }

        // 2-column grid layout
        binding.recyclerFoods.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerFoods.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
