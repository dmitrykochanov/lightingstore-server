package com.dmko.lightingstore.users.entities

data class UserEntity(
        val id: Long = 0,
        val login: String,
        val password: String
)
