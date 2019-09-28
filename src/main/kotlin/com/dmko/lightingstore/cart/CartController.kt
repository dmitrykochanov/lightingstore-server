package com.dmko.lightingstore.cart

import com.dmko.lightingstore.cart.entity.Cart
import com.dmko.lightingstore.products.ProductsService
import com.dmko.lightingstore.products.entity.ProductResponse
import com.dmko.lightingstore.users.entity.UserEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(
        private val cartDao: CartDao,
        private val productsService: ProductsService
) {

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    fun getProducts(@AuthenticationPrincipal user: UserEntity): List<ProductResponse> =
            productsService.getProductsFromCart(user.id)

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    fun addProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        val cart = Cart(productId, user.id)
        cartDao.insertProduct(cart)
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    fun removeProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        cartDao.removeProduct(user.id, productId)
    }
}