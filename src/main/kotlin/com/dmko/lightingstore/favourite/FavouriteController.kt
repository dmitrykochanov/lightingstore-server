package com.dmko.lightingstore.favourite

import com.dmko.lightingstore.favourite.entity.Favourite
import com.dmko.lightingstore.products.ProductsService
import com.dmko.lightingstore.products.entity.ProductResponse
import com.dmko.lightingstore.users.entity.UserEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/favourite")
class FavouriteController(
        private val favouriteDao: FavouriteDao,
        private val productsService: ProductsService
) {

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    fun getProducts(@AuthenticationPrincipal user: UserEntity): List<ProductResponse> =
            productsService.getProductsFromFavourite(user.id)

    @CrossOrigin
    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    fun addProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        val favourite = Favourite(productId, user.id)
        favouriteDao.insertProduct(favourite)
    }

    @CrossOrigin
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    fun removeProduct(
            @PathVariable productId: Long,
            @AuthenticationPrincipal user: UserEntity
    ) {
        favouriteDao.removeProduct(user.id, productId)
    }
}