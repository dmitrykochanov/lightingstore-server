package com.dmko.lightingstore.users.entity

data class SignUpRequest(
        val login: String,
        val name: String,
        val password: String
)