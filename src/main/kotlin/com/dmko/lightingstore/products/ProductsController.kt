package com.dmko.lightingstore.products

import com.dmko.lightingstore.products.entity.Product
import com.dmko.lightingstore.products.entity.ProductRequest
import com.dmko.lightingstore.products.entity.ProductResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class ProductsController(
        private val productsDao: ProductsDao
) {

    @CrossOrigin
    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('USER')")
    fun getCategories() = productsDao.getCategories()

    @CrossOrigin
    @GetMapping("/products/{categoryId}")
    @PreAuthorize("hasAuthority('USER')")
    fun getProducts(@PathVariable categoryId: Long): List<ProductResponse> {
        val products = productsDao.getProducts(categoryId)

        return products.map { product ->
            ProductResponse(
                    id = product.id,
                    categoryId = product.categoryId,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    count = product.count,
                    // TODO
                    images = emptyList(),
                    // TODO
                    inFavourites = false,
                    // TODO
                    inCart = false,
                    material = product.material,
                    color = product.color,
                    width = product.width,
                    height = product.height,
                    lampCount = product.lampCount
            )
        }
    }

    @CrossOrigin
    @PostMapping("/products")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun insertProduct(@RequestBody productRequest: ProductRequest) {
        productsDao.insertProduct(productRequest)
        // TODO images
    }

    @CrossOrigin
    @PutMapping("/products")
    fun updateProduct(@RequestBody productRequest: ProductRequest) {
        productsDao.updateProduct(productRequest)
        // TODO images
    }
}