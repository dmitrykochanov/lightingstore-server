package com.dmko.lightingstore.users.entities

data class AuthRequest(
        val login: String,
        val password: String
)
