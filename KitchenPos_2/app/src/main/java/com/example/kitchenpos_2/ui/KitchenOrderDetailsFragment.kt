package com.example.kitchenpos_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitchenpos_2.adapter.KitchenOrderItemsAdapter
import com.example.kitchenpos_2.api.ApiClient
import com.example.kitchenpos_2.databinding.FragmentKitchenOrderDetailsBinding
import com.example.kitchenpos_2.model.OrderResponse
import com.example.kitchenpos_2.model.StatusRequest
import kotlinx.coroutines.launch
import retrofit2.Response

class KitchenOrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentKitchenOrderDetailsBinding
    private var orderId: Int = 0
    private var currentStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderId = arguments?.getInt(ARG_ORDER_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentKitchenOrderDetailsBinding.inflate(inflater, container, false)
        setupButtons()
        fetchOrderDetails()
        return binding.root
    }

    private fun setupButtons() {
        // Update status to "Preparing"
        binding.btnPreparing.setOnClickListener {
            updateOrderStatus("Preparing")
        }

        // Update status to "Served"
        binding.btnServed.setOnClickListener {
            updateOrderStatus("Served")
        }
    }

    private fun fetchOrderDetails() {
        lifecycleScope.launch {
            try {
                val response: Response<OrderResponse> = ApiClient.apiService.getOrder(orderId)
                if (response.isSuccessful) {
                    val order = response.body()
                    if (order != null) {
                        currentStatus = order.status

                        binding.txtOrderId.text = "Order ID: ${order.order_id}"
                        binding.txtTotal.text = "Total: ₹${order.total_price}"
                        binding.txtStatus.text = "Status: ${order.status}"

                        binding.recyclerOrderItems.layoutManager = LinearLayoutManager(requireContext())
                        binding.recyclerOrderItems.adapter = KitchenOrderItemsAdapter(order.items)

                        // 🔥 Make both buttons always clickable
                        binding.btnPreparing.isEnabled = true
                        binding.btnServed.isEnabled = true
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load order", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateOrderStatus(newStatus: String) {
        lifecycleScope.launch {
            try {
                val response: Response<OrderResponse> =
                    ApiClient.apiService.updateOrderStatus(orderId, StatusRequest(newStatus))

                if (response.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Order updated to $newStatus",
                        Toast.LENGTH_SHORT
                    ).show()
                    fetchOrderDetails() // Refresh UI
                } else {
                    Toast.makeText(requireContext(), "Failed to update status", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val ARG_ORDER_ID = "order_id"
        fun newInstance(orderId: Int): KitchenOrderDetailsFragment {
            val fragment = KitchenOrderDetailsFragment()
            val args = Bundle()
            args.putInt(ARG_ORDER_ID, orderId)
            fragment.arguments = args
            return fragment
        }
    }
}
