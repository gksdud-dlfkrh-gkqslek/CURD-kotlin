package com.bs.crudkotlin.Controller

import com.bs.crudkotlin.DTO.SignUpRequest
import com.bs.crudkotlin.DTO.UserResponse
import com.bs.crudkotlin.Entity.UserEntity
import com.bs.crudkotlin.Repository.UserRepository
import com.bs.crudkotlin.Service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    //회원가입
    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<UserResponse?>? {
        val user = authService.signUp(request)
        return ResponseEntity.ok(user)
    }
}