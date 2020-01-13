package com.dmko.lightingstore.cart

import com.dmko.lightingstore.cart.entity.Cart
import com.dmko.lightingstore.products.entity.Product
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface CartDao {

    @Select("""SELECT products.* FROM products JOIN cart ON cart.product_id = products.id 
        WHERE cart.user_id = #{userId}""")
    fun getProducts(userId: Long): List<Product>

    @Insert("INSERT INTO cart(product_id, user_id, count) VALUES(#{productId}, #{userId}, #{count})")
    fun insertProduct(cart: Cart)

    @Update("UPDATE cart SET count=#{count} WHERE product_id = #{productId} AND user_id = #{userId}")
    fun updateCartCount(cart: Cart)

    @Select("SELECT product_id, user_Id, count FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    fun getCart(userId: Long, productId: Long): Cart?

    @Delete("DELETE FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    fun removeProduct(userId: Long, productId: Long)
}