package com.dmko.lightingstore.products

import com.dmko.lightingstore.cart.CartDao
import com.dmko.lightingstore.favourite.FavouriteDao
import com.dmko.lightingstore.orders.OrdersDao
import com.dmko.lightingstore.orders.entity.OrderProductResponse
import com.dmko.lightingstore.products.entity.Product
import com.dmko.lightingstore.products.entity.ProductResponse
import org.springframework.stereotype.Service

@Service
class ProductsService(
        private val productsDao: ProductsDao,
        private val cartDao: CartDao,
        private val favouriteDao: FavouriteDao,
        private val ordersDao: OrdersDao
) {

    fun getProducts(userId: Long): List<ProductResponse> {
        val products = productsDao.getAllProducts()
        return getProductResponses(userId, products)
    }

    fun getProductsFromCategory(userId: Long, categoryId: Long): List<ProductResponse> {
        val products = productsDao.getProducts(categoryId)
        return getProductResponses(userId, products)
    }

    fun getProductsFromCart(userId: Long): List<ProductResponse> {
        val products = cartDao.getProducts(userId)
        return getProductResponses(userId, products)
    }

    fun getProductsFromFavourite(userId: Long): List<ProductResponse> {
        val products = favouriteDao.getProducts(userId)
        return getProductResponses(userId, products)
    }

    fun getProductsFromOrder(userId: Long, orderId: Long): List<OrderProductResponse> {
        val products = ordersDao.getProducts(orderId)
        return getOrderProductResponses(userId, orderId, products)
    }

    private fun getProductResponses(userId: Long, products: List<Product>): List<ProductResponse> {
        val productsFromCart = cartDao.getProducts(userId)
        val productsFromFavourite = favouriteDao.getProducts(userId)
        return products.map { product ->
            mapProductResponse(
                    product = product,
                    inFavourites = productsFromFavourite.any { it.id == product.id },
                    inCart = productsFromCart.any { it.id == product.id }
            )
        }
    }

    private fun mapProductResponse(
            product: Product,
            inFavourites: Boolean,
            inCart: Boolean
    ) = ProductResponse(
            id = product.id,
            categoryId = product.categoryId,
            name = product.name,
            description = product.description,
            price = product.price,
            count = product.count,
            inFavourites = inFavourites,
            inCart = inCart,
            material = product.material,
            color = product.color,
            width = product.width,
            height = product.height,
            lampCount = product.lampCount,
            image = product.image
    )

    private fun getOrderProductResponses(userId: Long, orderId: Long, products: List<Product>): List<OrderProductResponse> {
        val productsFromCart = cartDao.getProducts(userId)
        val productsFromFavourite = favouriteDao.getProducts(userId)
        return products.map { product ->
            mapOrderProductResponse(
                    product = product,
                    inFavourites = productsFromFavourite.any { it.id == product.id },
                    inCart = productsFromCart.any { it.id == product.id },
                    orderCount = ordersDao.getProductCount(orderId, product.id)
            )
        }
    }

    private fun mapOrderProductResponse(
            product: Product,
            inFavourites: Boolean,
            inCart: Boolean,
            orderCount: Long
    ) = OrderProductResponse(
            id = product.id,
            categoryId = product.categoryId,
            name = product.name,
            description = product.description,
            price = product.price,
            count = product.count,
            inFavourites = inFavourites,
            inCart = inCart,
            material = product.material,
            color = product.color,
            width = product.width,
            height = product.height,
            lampCount = product.lampCount,
            image = product.image,
            orderCount = orderCount
    )
}