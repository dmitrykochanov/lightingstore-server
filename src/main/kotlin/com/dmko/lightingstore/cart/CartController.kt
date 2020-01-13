package com.dmko.lightingstore.cart

import com.dmko.lightingstore.cart.entity.Cart
import com.dmko.lightingstore.cart.entity.CartProductResponse
import com.dmko.lightingstore.products.ProductsService
import com.dmko.lightingstore.products.entity.ProductResponse
import com.dmko.lightingstore.users.entity.UserEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(
        private val cartDao: CartDao,
        private val productsService: ProductsService
) {

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    fun getProducts(@AuthenticationPrincipal user: UserEntity): List<CartProductResponse> =
            productsService.getProductsFromCart(user.id)

    @CrossOrigin
    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    fun addProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        val existingCart = cartDao.getCart(user.id, productId)
        if (existingCart != null) {
            val updatedCart = existingCart.copy(count = existingCart.count + 1)
            cartDao.updateCartCount(updatedCart)
        } else {
            val newCart = Cart(productId, user.id, 1)
            cartDao.insertProduct(newCart)
        }
    }

    @CrossOrigin
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    fun removeProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        val existingCart = cartDao.getCart(user.id, productId)
        if (existingCart != null && existingCart.count >= 2) {
            val newCart = existingCart.copy(count = existingCart.count - 1)
            cartDao.updateCartCount(newCart)
        } else {
            cartDao.removeProduct(user.id, productId)
        }
    }
}