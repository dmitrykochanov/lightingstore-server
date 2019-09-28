package com.dmko.lightingstore.products.entity

data class Product @JvmOverloads constructor(
        val id: Long,
        val categoryId: Long,
        val name: String,
        val description: String,
        val price: Float,
        val count: Int,
        val material: String? = null,
        val color: String? = null,
        val width: String? = null,
        val height: String? = null,
        val lampCount: Int? = null
)