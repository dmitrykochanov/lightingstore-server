package com.dmko.lightingstore.users.entity

data class UserEntity(
        val id: Long = 0,
        val login: String,
        val name: String,
        val email: String,
        val password: String
)
