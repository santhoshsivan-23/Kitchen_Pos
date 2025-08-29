package com.example.kitchenpos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitchenpos.adapter.CartAdapter
import com.example.kitchenpos.api.ApiClient
import com.example.kitchenpos.databinding.FragmentCartBinding
import com.example.kitchenpos.model.CartManager
import com.example.kitchenpos.model.OrderItem
import com.example.kitchenpos.model.OrderRequest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartItems = CartManager.getCartItems()
        cartAdapter = CartAdapter(cartItems) {
            updateTotalPrice()
        }

        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCart.adapter = cartAdapter

        updateTotalPrice()

        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }

        return binding.root
    }

    private fun updateTotalPrice() {
        val total = CartManager.getTotalPrice()
        binding.txtTotal.text = "Total: ₹$total"
    }

    private fun placeOrder() {
        val cartItems = cartAdapter.getCartItems()
        if (cartItems.isEmpty()) {
            Toast.makeText(requireContext(), "Cart is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        // ✅ Map cart to food_id, food_name, quantity, price
        val orderItems = cartItems.map { (food, qty) ->
            OrderItem(
                food_id = food.id,
                food_name = food.name,
                quantity = qty,
                price = food.price
            )
        }

        val orderRequest = OrderRequest(orderItems)

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.createOrder(orderRequest)
                if (response.isSuccessful && response.body() != null) {
                    val orderResponse = response.body()!!
                    Toast.makeText(
                        requireContext(),
                        "Order #${orderResponse.order_id} placed! Status: ${orderResponse.status}",
                        Toast.LENGTH_LONG
                    ).show()

                    CartManager.clearCart()

                    parentFragmentManager.beginTransaction()
                        .replace((view?.parent as ViewGroup).id, OrdersFragment())
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "Failed to place order", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }}
    }
