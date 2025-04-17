package com.example.thefoodhub.shared

object Constants {
    const val SUPABASE_URL = "YOUR_SUPABASE_URL"
    const val SUPABASE_ANON_KEY = "YOUR_SUPABASE_ANON_KEY"
    
    // Table names
    const val TABLE_FOOD_ITEMS = "food_items"
    const val TABLE_ORDERS = "orders"
    const val TABLE_ORDER_ITEMS = "order_items"
    const val TABLE_USERS = "users"
    
    // Order statuses
    const val ORDER_STATUS_PENDING = "pending"
    const val ORDER_STATUS_CONFIRMED = "confirmed"
    const val ORDER_STATUS_PREPARING = "preparing"
    const val ORDER_STATUS_READY = "ready"
    const val ORDER_STATUS_DELIVERED = "delivered"
    const val ORDER_STATUS_CANCELLED = "cancelled"
} 