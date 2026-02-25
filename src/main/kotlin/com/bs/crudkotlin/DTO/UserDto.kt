package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.UserEntity
import com.bs.crudkotlin.Entity.UserRole
import jakarta.validation.constraints.Pattern

// 회원가입 요청
data class SignUpRequest(
    @field:Pattern(
        regexp = "^010-\\d{4}-\\d{4}$",
        message = "전화번호 형식이 올바르지 않습니다 (010-0000-0000)"
    )
    val phone: String,
    val password: String,
    val name: String
)

// 로그인 요청
data class LoginRequest(
    val phone: String,
    val password: String
)

// 사용자 정보 응답 (비밀번호 제외)
data class UserResponse(
    val id: String,
    val phone: String,
    val name: String,
    val role: UserRole
) {
    companion object {
        fun from(entity: UserEntity) = UserResponse(
            id = entity.id,
            phone = entity.phone,
            name = entity.name,
            role = entity.role
        )
    }
}