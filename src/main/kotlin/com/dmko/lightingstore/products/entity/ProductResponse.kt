package com.dmko.lightingstore.products.entity

data class ProductResponse(
        val id: Long,
        val categoryId: Long,
        val name: String,
        val description: String,
        val price: Float,
        val count: Int,
        val inFavourites: Boolean,
        val inCart: Boolean,
        val material: String? = null,
        val color: String? = null,
        val width: String? = null,
        val height: String? = null,
        val lampCount: Int? = null,
        val image: String? = null
)