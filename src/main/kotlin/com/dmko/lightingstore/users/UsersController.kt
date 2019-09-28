package com.dmko.lightingstore.users

import com.dmko.lightingstore.users.entity.AuthRequest
import com.dmko.lightingstore.users.entity.AuthResponse
import com.dmko.lightingstore.users.entity.UserEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(
        private val usersDao: UsersDao,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val tokenProvider: TokenProvider
) {

    @CrossOrigin
    @PostMapping("/sign-up")
    fun signUp(@RequestBody authRequest: AuthRequest): AuthResponse {
        val newUser = UserEntity(
                login = authRequest.login,
                password = bCryptPasswordEncoder.encode(authRequest.password)
        )
        usersDao.insertUser(newUser)
        val insertedUser = usersDao.findUserByLogin(authRequest.login)!!
        usersDao.addRoleToUser(insertedUser.id, 1)
        val roles = usersDao.getUserRoles(insertedUser.id)

        return tokenProvider.createAuthResponse(insertedUser, roles)
    }

    @CrossOrigin
    @PostMapping("/sign-in")
    fun signIn(@RequestBody authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        val user = usersDao.findUserByLogin(authRequest.login)

        return if (user != null && BCrypt.checkpw(authRequest.password, user.password)) {
            val roles = usersDao.getUserRoles(user.id)
            ResponseEntity(tokenProvider.createAuthResponse(user, roles), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
