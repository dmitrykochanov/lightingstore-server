package com.dmko.lightingstore.favourite

import com.dmko.lightingstore.favourite.entity.Favourite
import com.dmko.lightingstore.products.ProductsService
import com.dmko.lightingstore.products.entity.ProductResponse
import com.dmko.lightingstore.users.entity.UserEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/favourite")
class FavouriteController(
        private val favouriteDao: FavouriteDao,
        private val productsService: ProductsService
) {

    @GetMapping
    fun getProducts(@AuthenticationPrincipal user: UserEntity): List<ProductResponse> =
            productsService.getProductsFromFavourite(user.id)

    @PutMapping("/{productId}")
    fun addProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        val favourite = Favourite(productId, user.id)
        favouriteDao.insertProduct(favourite)
    }

    @DeleteMapping("/{productId}")
    fun removeProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        favouriteDao.removeProduct(user.id, productId)
    }
}