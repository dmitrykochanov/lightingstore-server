package com.dmko.lightingstore.products.entity

data class ProductRequest(
        val id: Long? = null,
        val categoryId: Long,
        val name: String,
        val description: String,
        val price: Float,
        val count: Int,
        val images: List<String>,
        val material: String? = null,
        val color: String? = null,
        val width: String? = null,
        val height: String? = null,
        val lampCount: Int? = null
)