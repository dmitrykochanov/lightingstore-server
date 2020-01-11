package com.dmko.lightingstore.orders.entity

import com.dmko.lightingstore.users.entity.UserResponse

data class OrderResponse(
        val id: Long,
        val status: String,
        val user: UserResponse,
        val createDate: Long,
        val products: List<OrderProductResponse>
)