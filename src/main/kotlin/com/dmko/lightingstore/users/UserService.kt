package com.dmko.lightingstore.users

import com.dmko.lightingstore.users.entity.UserResponse
import org.springframework.stereotype.Service

@Service
class UserService(
        private val usersDao: UsersDao
) {

    fun getUser(userId: Long): UserResponse {
        val user = usersDao.findUserById(userId)
        return UserResponse(
                id = user.id,
                name = user.name,
                login = user.login,
                email = user.email
        )
    }
}