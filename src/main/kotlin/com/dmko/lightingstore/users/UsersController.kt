package com.dmko.lightingstore.users

import com.dmko.lightingstore.users.entity.SignInRequest
import com.dmko.lightingstore.users.entity.AuthResponse
import com.dmko.lightingstore.users.entity.SignUpRequest
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
    fun signUp(@RequestBody signUpRequest: SignUpRequest): AuthResponse {
        val newUser = UserEntity(
                login = signUpRequest.login,
                name = signUpRequest.name,
                password = bCryptPasswordEncoder.encode(signUpRequest.password)
        )
        usersDao.insertUser(newUser)
        val insertedUser = usersDao.findUserByLogin(signUpRequest.login)!!
        usersDao.addRoleToUser(insertedUser.id, 1)
        val roles = usersDao.getUserRoles(insertedUser.id)

        return tokenProvider.createAuthResponse(insertedUser, roles)
    }

    @CrossOrigin
    @PostMapping("/sign-in")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<AuthResponse> {
        val user = usersDao.findUserByLogin(signInRequest.login)

        return if (user != null && BCrypt.checkpw(signInRequest.password, user.password)) {
            val roles = usersDao.getUserRoles(user.id)
            ResponseEntity(tokenProvider.createAuthResponse(user, roles), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
