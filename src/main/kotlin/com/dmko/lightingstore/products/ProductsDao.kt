package com.dmko.lightingstore.products

import com.dmko.lightingstore.products.entity.Category
import com.dmko.lightingstore.products.entity.Product
import com.dmko.lightingstore.products.entity.ProductImage
import com.dmko.lightingstore.products.entity.ProductRequest
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface ProductsDao {

    @Select("SELECT * FROM categories")
    fun getCategories(): List<Category>

    @Select("SELECT * FROM products WHERE category_id = #{categoryId} ")
    fun getProducts(categoryId: Long): List<Product>

    @Insert("""INSERT INTO products(category_id, name, description, price, count, material, color, width, height, lamp_count)
        VALUES(#{categoryId}, #{name}, #{description}, #{price}, #{count}, #{material}, #{color}, #{width}, #{height}, #{lampCount})""")
    fun insertProduct(productRequest: ProductRequest)

    @Update("""UPDATE products SET category_id = #{categoryId}, name = #{name}, description = #{description}, 
        price = #{price}, count = #{count}, material = #{material}, color = #{color}, width = #{width}, height = #{height},
         lamp_count = #{lampCount} WHERE id=#{id}""")
    fun updateProduct(productRequest: ProductRequest)

    @Select("SELECT product_id, url FROM product_images WHERE product_id = #{productId}")
    fun getProductImages(productId: Long): List<ProductImage>

    @Insert("INSERT INTO product_images(product_id, url) VALUES(#{productId}, #{url})")
    fun insertProductImage(productImages: ProductImage)

    @Delete("DELETE FROM product_images WHERE product_id = #{productId}")
    fun deleteProductImages(productId: Long)

    @Select("SELECT LAST_INSERT_ID()")
    fun getLastInsertId(): Long
}