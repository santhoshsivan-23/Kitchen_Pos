package com.example.kitchenpos_2.api

import com.example.kitchenpos_2.model.OrderResponse
import com.example.kitchenpos_2.model.StatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("orders")
    suspend fun getOrders(): Response<List<OrderResponse>>

    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") orderId: Int): Response<OrderResponse>

    @PUT("orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") orderId: Int,
        @Body statusRequest: StatusRequest
    ): Response<OrderResponse>
}
