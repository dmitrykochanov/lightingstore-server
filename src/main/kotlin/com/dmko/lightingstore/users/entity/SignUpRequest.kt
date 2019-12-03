package com.dmko.lightingstore.users.entity

data class SignUpRequest(
        val login: String,
        val name: String,
        val email: String,
        val password: String
)