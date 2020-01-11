package com.dmko.lightingstore.orders

import com.dmko.lightingstore.cart.CartDao
import com.dmko.lightingstore.orders.entity.Order
import com.dmko.lightingstore.orders.entity.OrderResponse
import com.dmko.lightingstore.products.ProductsService
import com.dmko.lightingstore.users.UserService
import com.dmko.lightingstore.users.UsersDao
import com.dmko.lightingstore.users.entity.RoleEntity
import com.dmko.lightingstore.users.entity.UserEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrdersController(
        private val ordersDao: OrdersDao,
        private val usersDao: UsersDao,
        private val cartDao: CartDao,
        private val userService: UserService,
        private val productsService: ProductsService
) {

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    fun getOrders(@AuthenticationPrincipal user: UserEntity): List<OrderResponse> {
        val orders = ordersDao.getOrders(user.id)
        val userResponse = userService.getUser(user.id)

        return orders.map { order ->
            OrderResponse(
                    id = order.id,
                    status = order.status,
                    user = userResponse,
                    createDate = order.createDate,
                    products = productsService.getProductsFromOrder(user.id, order.id)
            )
        }
    }

    @CrossOrigin
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun getAllOrders(): List<OrderResponse> {
        val orders = ordersDao.getAllOrders()

        return orders.map { order ->
            OrderResponse(
                    id = order.id,
                    status = order.status,
                    user = userService.getUser(order.userId),
                    createDate = order.createDate,
                    products = productsService.getProductsFromOrder(order.userId, order.id)
            )
        }
    }

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    fun insertOrder(
            @RequestBody productIds: List<Long>,
            @AuthenticationPrincipal user: UserEntity
    ) {
        val order = Order(
                id = 0,
                userId = user.id,
                status = Order.STATUS_CREATED,
                createDate = System.currentTimeMillis()
        )
        val insertedOrderId = ordersDao.insertOrder(order)

        productIds.forEach { productId ->
            ordersDao.insertProductOrder(productId, insertedOrderId)
            cartDao.removeProduct(user.id, productId)
        }
    }

    @CrossOrigin
    @PutMapping("/{orderId}/status/{status}")
    @PreAuthorize("hasAuthority('USER')")
    fun updateOrderStatus(
            @PathVariable orderId: Long,
            @PathVariable status: String,
            @AuthenticationPrincipal user: UserEntity
    ): ResponseEntity<Unit> {
        if (status !in listOf(Order.STATUS_CREATED, Order.STATUS_PROCESSING, Order.STATUS_CANCELLED, Order.STATUS_COMPLETED)) {
            return ResponseEntity.badRequest().build()
        }

        val isAdmin = usersDao.getUserRoles(user.id).map(RoleEntity::id).contains(2)
        val order = ordersDao.getOrderById(orderId)
        return if (isAdmin || (order.userId == user.id && status == Order.STATUS_CREATED || status == Order.STATUS_CANCELLED)) {
            ordersDao.updateOrderStatus(orderId, status)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @CrossOrigin
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAuthority('USER')")
    fun deleteOrder(
            @PathVariable orderId: Long,
            @AuthenticationPrincipal user: UserEntity
    ): ResponseEntity<Unit> {
        val isAdmin = usersDao.getUserRoles(user.id).map(RoleEntity::id).contains(2)
        val order = ordersDao.getOrderById(orderId)

        return if (isAdmin || order.userId == user.id) {
            ordersDao.deleteOrder(orderId)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}