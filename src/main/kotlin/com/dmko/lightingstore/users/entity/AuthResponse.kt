package com.dmko.lightingstore.users.entity

data class AuthResponse(
        val login: String,
        val token: String,
        val roles: List<RoleEntity>
)
