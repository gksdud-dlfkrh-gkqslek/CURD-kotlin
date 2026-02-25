package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.SignUpRequest
import com.bs.crudkotlin.DTO.UserResponse
import com.bs.crudkotlin.Entity.UserEntity
import com.bs.crudkotlin.Repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun signUp(request: SignUpRequest): UserResponse {
        //전화번호 중복 체크
        if (userRepository.existsByPhone(request.phone)) {
            throw IllegalArgumentException("이미 사용 중인 전화번호입니다.")
        }

        //비밀번호 암호화 후 저장
        val user = UserEntity(
            phone = request.phone,
            password = passwordEncoder.encode(request.password),
            name = request.name
        )
        val savedUser = userRepository.save(user)
        return UserResponse.from(savedUser)
    }
}