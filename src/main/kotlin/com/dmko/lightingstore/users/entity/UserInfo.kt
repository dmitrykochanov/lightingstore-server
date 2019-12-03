package com.dmko.lightingstore.users.entity

data class UserInfo(
        val login: String,
        val name: String,
        val email: String,
        val roles: List<RoleEntity>
)