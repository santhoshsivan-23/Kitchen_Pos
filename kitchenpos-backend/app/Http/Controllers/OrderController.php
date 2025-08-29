<?php

namespace App\Http\Controllers;

use App\Models\Order;
use App\Models\OrderItem;
use Illuminate\Http\Request;

class OrderController extends Controller
{
    // 📌 Get all orders with their items
    public function index()
    {
        $orders = Order::with('items')->get();
        return response()->json($orders);
    }

    // 📌 Get single order by ID
    public function show($id)
    {
        $order = Order::with('items')->findOrFail($id);
        return response()->json($order);
    }

    // 📌 Create a new order
    public function store(Request $request)
    {
        // Calculate total price from request items
        $total = 0;
        foreach ($request->items as $item) {
            $total += $item['quantity'] * $item['price'];
        }

        // ✅ Create the order (auto-generates unique order_id)
        $order = Order::create([
            'total_price' => $total,
            'status' => 'pending',
        ]);

        // ✅ Create order items
        foreach ($request->items as $item) {
            OrderItem::create([
                'order_id' => $order->id,
                'food_name' => $item['food_name'],
                'quantity' => $item['quantity'],
                'price' => $item['price'],
            ]);
        }

        // ✅ Return full response with unique ID + items
        return response()->json([
            'order_id'    => $order->id,
            'status'      => $order->status,
            'total_price' => $order->total_price,
            'items'       => $order->items()->get(),
        ], 201);
    }

    // 📌 Update order status
public function updateStatus(Request $request, $id)
{
    $order = Order::findOrFail($id);

    // ✅ read the `status` field from request
    $order->status = $request->input('status'); 
    $order->save();

    return response()->json([
        'message' => 'Status updated successfully',
        'order'   => $order,
    ], 200);
}

}
