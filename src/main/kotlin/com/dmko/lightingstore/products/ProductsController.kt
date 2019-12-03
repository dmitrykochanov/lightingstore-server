package com.dmko.lightingstore.products

import com.dmko.lightingstore.products.entity.ProductImage
import com.dmko.lightingstore.products.entity.ProductRequest
import com.dmko.lightingstore.products.entity.ProductResponse
import com.dmko.lightingstore.users.entity.UserEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ProductsController(
        private val productsDao: ProductsDao,
        private val productsService: ProductsService
) {

    @CrossOrigin
    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('USER')")
    fun getCategories() = productsDao.getCategories()

    @CrossOrigin
    @GetMapping("/products/{categoryId}")
    @PreAuthorize("hasAuthority('USER')")
    fun getProducts(
            @PathVariable categoryId: Long,
            @AuthenticationPrincipal user: UserEntity
    ): List<ProductResponse> = productsService.getProductsFromCategory(user.id, categoryId)

    @CrossOrigin
    @GetMapping("/products")
    @PreAuthorize("hasAuthority('USER')")
    fun getProducts(
            @AuthenticationPrincipal user: UserEntity
    ): List<ProductResponse> = productsService.getProducts(user.id)

    @CrossOrigin
    @PostMapping("/products")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun insertProduct(@RequestBody productRequest: ProductRequest) {
        val productId = productsDao.insertProduct(productRequest)

        productRequest.images.forEach { url ->
            val productImage = ProductImage(productId, url)
            productsDao.insertProductImage(productImage)
        }
    }

    @CrossOrigin
    @PutMapping("/products")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun updateProduct(@RequestBody productRequest: ProductRequest) {
        val productId = productRequest.id!!
        productsDao.updateProduct(productRequest)

        productsDao.deleteProductImages(productId)
        productRequest.images.forEach { url ->
            val productImage = ProductImage(productId, url)
            productsDao.insertProductImage(productImage)
        }
    }
}