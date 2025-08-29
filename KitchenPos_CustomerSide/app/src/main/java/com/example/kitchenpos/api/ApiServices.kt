package com.example.kitchenpos.api

import com.example.kitchenpos.model.OrderRequest
import com.example.kitchenpos.model.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("orders")
    suspend fun createOrder(@Body orderRequest: OrderRequest): Response<OrderResponse>

    @GET("orders")
    suspend fun getOrders(): Response<List<OrderResponse>>

    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") id: Int): Response<OrderResponse>
}
