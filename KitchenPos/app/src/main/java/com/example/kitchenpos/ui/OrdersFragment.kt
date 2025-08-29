package com.example.kitchenpos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitchenpos.adapter.OrdersAdapter
import com.example.kitchenpos.api.ApiClient
import com.example.kitchenpos.databinding.FragmentOrdersBinding
import kotlinx.coroutines.launch

class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        fetchOrders()
        return binding.root
    }

    private fun fetchOrders() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getOrders()
                if (response.isSuccessful) {
                    val orders = response.body() ?: emptyList()
                    binding.recyclerOrders.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerOrders.adapter = OrdersAdapter(orders)
                } else {
                    Toast.makeText(requireContext(), "Failed to load orders", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
