package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.UserEntity
import com.bs.crudkotlin.Entity.UserRole

// 회원가입 요청
data class SignUpRequest(
    val phone: Long,
    val password: String,
    val name: String
)

// 로그인 요청
data class LoginRequest(
    val phone: Long,
    val password: String
)

// 사용자 정보 응답 (비밀번호 제외)
data class UserResponse(
    val id: String,
    val phone: Long,
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