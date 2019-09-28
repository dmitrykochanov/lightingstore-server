package com.dmko.lightingstore.products

import com.dmko.lightingstore.cart.CartDao
import com.dmko.lightingstore.products.entity.Product
import com.dmko.lightingstore.products.entity.ProductResponse
import org.springframework.stereotype.Service

@Service
class ProductsService(
        private val productsDao: ProductsDao,
        private val cartDao: CartDao
) {

    fun getProductsFromCategory(userId: Long, categoryId: Long): List<ProductResponse> {
        val products = productsDao.getProducts(categoryId)
        return getProductResponses(userId, products)
    }

    fun getProductsFromCart(userId: Long): List<ProductResponse> {
        val products = cartDao.getProducts(userId)
        return getProductResponses(userId, products)
    }

    private fun getProductResponses(userId: Long, products: List<Product>): List<ProductResponse> {
        val productsFromCart = cartDao.getProducts(userId)
        return products.map { product ->
            mapProductResponse(
                    product = product,
                    // TODO
                    images = emptyList(),
                    inFavourites = false,
                    inCart = productsFromCart.any { it.id == product.id }
            )
        }
    }

    private fun mapProductResponse(
            product: Product,
            images: List<String>,
            inFavourites: Boolean,
            inCart: Boolean
    ) = ProductResponse(
            id = product.id,
            categoryId = product.categoryId,
            name = product.name,
            description = product.description,
            price = product.price,
            count = product.count,
            images = images,
            inFavourites = inFavourites,
            inCart = inCart,
            material = product.material,
            color = product.color,
            width = product.width,
            height = product.height,
            lampCount = product.lampCount
    )
}