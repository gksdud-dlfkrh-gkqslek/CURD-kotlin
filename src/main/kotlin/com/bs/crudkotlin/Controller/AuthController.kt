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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    //로그아웃
    @PostMapping("/logout")
    fun logout(session: HttpSession): ResponseEntity<Map<String, String>> {
        authService.logout(session)
        return ResponseEntity.ok(mapOf("message" to "로그아웃 되었습니다."))
    }

    // 내 정보 조회
    @GetMapping("/me")
    fun me(session: HttpSession): ResponseEntity<Any> {
        val user = authService.getCurrentUser(session)
            ?: return ResponseEntity.status(401).body(mapOf("error" to "로그인이 필요합니다."))

        return ResponseEntity.ok(user)
    }

    //모든 사용자 조회
    @GetMapping("/users")
    fun getAll(): ResponseEntity<List<UserResponse>>{
        return ResponseEntity.ok(authService.findAll())
    }

    //사용자 삭제
    @DeleteMapping("/user/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        authService.delete(id)
        return ResponseEntity.ok("삭제 완료")
    }

    // 승인 대기 목록 조회
    @GetMapping("/pending")
    fun getPendingUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(authService.findAllPending())
    }
}