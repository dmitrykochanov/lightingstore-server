package com.dmko.lightingstore.properties

import com.dmko.lightingstore.products.ProductsDao
import com.dmko.lightingstore.products.entity.Product
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/properties")
class PropertiesController(
        private val productsDao: ProductsDao
) {

    @CrossOrigin
    @GetMapping("/material/{categoryId}")
    @PreAuthorize("hasAuthority('USER')")
    fun getMaterials(
            @PathVariable categoryId: Long
    ): List<String> = productsDao
            .getProducts(categoryId)
            .mapNotNull(Product::material)
            .distinct()

    @CrossOrigin
    @GetMapping("/color/{categoryId}")
    @PreAuthorize("hasAuthority('USER')")
    fun getColors(
            @PathVariable categoryId: Long
    ): List<String> = productsDao
            .getProducts(categoryId)
            .mapNotNull(Product::color)
            .distinct()

    @CrossOrigin
    @GetMapping("/width/{categoryId}")
    @PreAuthorize("hasAuthority('USER')")
    fun getWidths(
            @PathVariable categoryId: Long
    ): List<String> = productsDao
            .getProducts(categoryId)
            .mapNotNull(Product::width)
            .distinct()

    @CrossOrigin
    @GetMapping("/height/{categoryId}")
    @PreAuthorize("hasAuthority('USER')")
    fun getHeights(
            @PathVariable categoryId: Long
    ): List<String> = productsDao
            .getProducts(categoryId)
            .mapNotNull(Product::height)
            .distinct()

    @CrossOrigin
    @GetMapping("/lampCount/{categoryId}")
    @PreAuthorize("hasAuthority('USER')")
    fun getLampCounts(
            @PathVariable categoryId: Long
    ): List<String> = productsDao
            .getProducts(categoryId)
            .mapNotNull(Product::lampCount)
            .distinct()
            .map(Int::toString)
}