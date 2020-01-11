package com.dmko.lightingstore.orders

import com.dmko.lightingstore.orders.entity.Order
import com.dmko.lightingstore.products.entity.Product
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface OrdersDao {

    @Select("""SELECT products.* FROM products JOIN products_orders ON products_orders.product_id = products.id 
        WHERE products_orders.order_id = #{orderId}""")
    fun getProducts(orderId: Long): List<Product>

    @Select("""SELECT count FROM products_orders WHERE order_id = #{orderId} AND product_id = #{productId}""")
    fun getProductCount(orderId: Long, productId: Long): Long

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    fun getOrders(userId: Long): List<Order>

    @Select("SELECT * FROM orders WHERE id = #{orderId}")
    fun getOrderById(orderId: Long): Order

    @Select("SELECT * FROM orders")
    fun getAllOrders(): List<Order>

    @Select("INSERT INTO orders(status, user_id, create_date) VALUES(#{status}, #{userId}, #{createDate}) RETURNING id")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    fun insertOrder(order: Order): Long

    @Insert("INSERT INTO products_orders(product_id, order_id, count) VALUES(#{productId}, #{orderId}, #{count})")
    fun insertProductOrder(productId: Long, orderId: Long, count: Long)

    @Update("UPDATE orders SET status = #{status} WHERE id = #{orderId}")
    fun updateOrderStatus(orderId: Long, status: String)

    @Delete("DELETE FROM orders WHERE id = #{orderId}")
    fun deleteOrder(orderId: Long)
}