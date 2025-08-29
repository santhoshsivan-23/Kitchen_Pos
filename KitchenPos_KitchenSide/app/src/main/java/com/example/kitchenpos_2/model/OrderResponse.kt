package com.example.kitchenpos_2.model

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id") val order_id: Int,
    val status: String,
    val total_price: Double,
    val items: List<OrderItemResponse>
)

data class OrderItemResponse(
    val id: Int,
    val order_id: Int,
    val food_name: String,
    val quantity: Int,
    val price: Double
)
