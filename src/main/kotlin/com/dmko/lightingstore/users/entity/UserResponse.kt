package com.dmko.lightingstore.users.entity

data class UserResponse(
        val id: Long,
        val login: String,
        val name: String,
        val email: String
)