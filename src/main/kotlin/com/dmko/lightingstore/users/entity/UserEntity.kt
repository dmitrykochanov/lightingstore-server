package com.dmko.lightingstore.users.entity

data class UserEntity(
        val id: Long = 0,
        val login: String,
        val password: String
)
