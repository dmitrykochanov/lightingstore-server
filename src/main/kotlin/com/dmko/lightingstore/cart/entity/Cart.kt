package com.dmko.lightingstore.cart.entity

data class Cart(
        val productId: Long,
        val userId: Long,
        val count: Long
)