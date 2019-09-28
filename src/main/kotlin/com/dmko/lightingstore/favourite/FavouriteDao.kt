package com.dmko.lightingstore.favourite

import com.dmko.lightingstore.favourite.entity.Favourite
import com.dmko.lightingstore.products.entity.Product
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface FavouriteDao {

    @Select("""SELECT products.* FROM products JOIN favourite ON favourite.product_id = products.id 
        WHERE favourite.user_id = #{userId}""")
    fun getProducts(userId: Long): List<Product>

    @Insert("INSERT INTO favourite(product_id, user_id) VALUES(#{productId}, #{userId})")
    fun insertProduct(favourite: Favourite)

    @Delete("DELETE FROM favourite WHERE user_id = #{userId} AND product_id = #{productId}")
    fun removeProduct(userId: Long, productId: Long)
}