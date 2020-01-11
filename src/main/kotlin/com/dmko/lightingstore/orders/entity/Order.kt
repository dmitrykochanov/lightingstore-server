package com.dmko.lightingstore.orders.entity

data class Order(
        val id: Long,
        val status: String,
        val userId: Long,
        val createDate: Long
) {

    companion object {

        const val STATUS_CREATED = "CREATED"
        const val STATUS_PROCESSING = "PROCESSING"
        const val STATUS_COMPLETED = "COMPLETED"
        const val STATUS_CANCELLED = "CANCELLED"
    }
}