package com.example.kitchenpos.model
import com.google.gson.annotations.SerializedName
import com.example.kitchenpos.R

// Food model
data class Food(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int
)

// Order request sent to backend
data class OrderRequest(
    val items: List<OrderItem>
)

// ✅ Include food_name & price also
data class OrderItem(
    val food_id: Int,
    val food_name: String,
    val quantity: Int,
    val price: Double
)

// Order response from backend
data class OrderResponse(
    @SerializedName("id") val order_id: Int,   // maps both "id" → order_id
    val status: String,
    val total_price: Double,
    val items: List<OrderItem>
)

// Each item inside the order response
data class OrderDetailItem(
    val food_name: String,
    val quantity: Int,
    val price: Double
)

// ✅ CartManager (manages cart state globally)
object CartManager {
    private val cartItems = mutableListOf<Pair<Food, Int>>()  // Pair<Food, Quantity>

    fun addToCart(food: Food) {
        val index = cartItems.indexOfFirst { it.first.id == food.id }
        if (index >= 0) {
            val (f, qty) = cartItems[index]
            cartItems[index] = Pair(f, qty + 1)
        } else {
            cartItems.add(Pair(food, 1))
        }
    }

    fun updateQuantity(food: Food, newQty: Int) {
        val index = cartItems.indexOfFirst { it.first.id == food.id }
        if (index >= 0) {
            if (newQty <= 0) {
                cartItems.removeAt(index)
            } else {
                cartItems[index] = Pair(food, newQty)
            }
        }
    }

    fun getCartItems(): MutableList<Pair<Food, Int>> = cartItems

    fun getTotalPrice(): Double {
        return cartItems.sumOf { (food, qty) -> food.price * qty }
    }

    fun clearCart() {
        cartItems.clear()
    }
}

// ✅ Dummy demo food items
val foods = listOf(
    Food(1, "Pizza", 250.0, R.drawable.pizza),
    Food(2, "Burger", 120.0, R.drawable.burger),
    Food(3, "Pasta", 180.0, R.drawable.pasta),
    Food(4, "Sandwich", 100.0, R.drawable.sandwitch),
    Food(5, "French Fries", 80.0, R.drawable.frenchfries),
    Food(6, "Kabab", 50.0, R.drawable.kabab)
)
