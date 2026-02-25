package com.bs.crudkotlin.Controller

import com.bs.crudkotlin.DTO.LoginRequest
import com.bs.crudkotlin.DTO.SignUpRequest
import com.bs.crudkotlin.DTO.UserResponse
import com.bs.crudkotlin.Entity.UserEntity
import com.bs.crudkotlin.Repository.UserRepository
import com.bs.crudkotlin.Service.AuthService
import jakarta.servlet.http.HttpSession
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

    //로그인
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
        session: HttpSession): ResponseEntity<UserResponse> {
        val user = authService.login(request, session)
        return ResponseEntity.ok(user)
    }
}