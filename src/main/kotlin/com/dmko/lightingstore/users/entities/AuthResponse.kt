package com.dmko.lightingstore.users.entities

data class AuthResponse(
        val login: String,
        val token: String,
        val roles: List<RoleEntity>
)
