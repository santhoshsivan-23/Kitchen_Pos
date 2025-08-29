package com.example.kitchenpos_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kitchenpos_2.adapter.KitchenOrdersAdapter
import com.example.kitchenpos_2.api.ApiClient
import com.example.kitchenpos_2.databinding.FragmentKitchenOrdersBinding
import kotlinx.coroutines.launch

class KitchenOrdersFragment : Fragment() {

    private lateinit var binding: FragmentKitchenOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKitchenOrdersBinding.inflate(inflater, container, false)
        setupRecycler()
        fetchOrders()
        return binding.root
    }

    private fun setupRecycler() {
        // 2-column grid
        binding.recyclerKitchenOrders.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun fetchOrders() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getOrders()
                if (response.isSuccessful) {
                    val orders = response.body() ?: emptyList()
                    binding.recyclerKitchenOrders.adapter =
                        KitchenOrdersAdapter(orders) { order ->
                            // Navigate to details fragment
                            val fragment = KitchenOrderDetailsFragment.newInstance(order.order_id)
                            parentFragmentManager.beginTransaction()
                                .replace(id, fragment)
                                .addToBackStack(null)
                                .commit()
                        }
                } else {
                    Toast.makeText(requireContext(), "Failed to load orders", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
