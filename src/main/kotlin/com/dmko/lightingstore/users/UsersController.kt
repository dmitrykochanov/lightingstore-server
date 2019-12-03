package com.dmko.lightingstore.users

import com.dmko.lightingstore.users.entity.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
class UsersController(
        private val usersDao: UsersDao,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val tokenProvider: TokenProvider
) {

    @CrossOrigin
    @PostMapping("/users/sign-up")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): AuthResponse {
        val newUser = UserEntity(
                login = signUpRequest.login,
                name = signUpRequest.name,
                email = signUpRequest.email,
                password = bCryptPasswordEncoder.encode(signUpRequest.password)
        )
        usersDao.insertUser(newUser)
        val insertedUser = usersDao.findUserByLogin(signUpRequest.login)!!
        usersDao.addRoleToUser(insertedUser.id, 1)
        val roles = usersDao.getUserRoles(insertedUser.id)

        return tokenProvider.createAuthResponse(insertedUser, roles)
    }

    @CrossOrigin
    @PostMapping("/users/sign-in")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<AuthResponse> {
        val user = usersDao.findUserByLogin(signInRequest.login)

        return if (user != null && BCrypt.checkpw(signInRequest.password, user.password)) {
            val roles = usersDao.getUserRoles(user.id)
            ResponseEntity(tokenProvider.createAuthResponse(user, roles), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @CrossOrigin
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    fun getUser(@AuthenticationPrincipal user: UserEntity): UserInfo {
        val roles = usersDao.getUserRoles(user.id)
        return UserInfo(
                login = user.login,
                name = user.name,
                email = user.email,
                roles = roles
        )
    }
}
