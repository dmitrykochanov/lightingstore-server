package com.dmko.lightingstore.products

import com.dmko.lightingstore.products.entity.Category
import com.dmko.lightingstore.products.entity.Product
import com.dmko.lightingstore.products.entity.ProductRequest
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface ProductsDao {

    @Select("SELECT * FROM categories")
    fun getCategories(): List<Category>

    @Select("SELECT * FROM products WHERE category_id = #{categoryId}")
    fun getProducts(categoryId: Long): List<Product>

    @Select("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Select("""INSERT INTO products(category_id, name, description, price, count, material, color, width, height, lamp_count, image)
        VALUES(#{categoryId}, #{name}, #{description}, #{price}, #{count}, #{material}, #{color}, #{width}, #{height}, #{lampCount}, #{image})""")
    fun insertProduct(productRequest: ProductRequest)

    @Update("""UPDATE products SET category_id = #{categoryId}, name = #{name}, description = #{description}, 
        price = #{price}, count = #{count}, material = #{material}, color = #{color}, width = #{width}, height = #{height},
         lamp_count = #{lampCount} WHERE id=#{id}""")
    fun updateProduct(productRequest: ProductRequest)
}