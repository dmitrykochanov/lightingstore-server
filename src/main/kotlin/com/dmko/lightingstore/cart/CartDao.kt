package com.dmko.lightingstore.cart

import com.dmko.lightingstore.cart.entity.Cart
import com.dmko.lightingstore.products.entity.Product
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface CartDao {

    @Select("""SELECT products.* FROM products JOIN cart ON cart.product_id = products.id 
        WHERE cart.user_id = #{userId}""")
    fun getProducts(userId: Long): List<Product>

    @Insert("INSERT INTO cart(product_id, user_id) VALUES(#{productId}, #{userId})")
    fun insertProduct(cart: Cart)

    @Delete("DELETE FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    fun removeProduct(userId: Long, productId: Long)
}