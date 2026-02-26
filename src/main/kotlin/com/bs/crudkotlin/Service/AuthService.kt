package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.LoginRequest
import com.bs.crudkotlin.DTO.SignUpRequest
import com.bs.crudkotlin.DTO.UserResponse
import com.bs.crudkotlin.Entity.UserEntity
import com.bs.crudkotlin.Repository.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
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

    //로그인
    fun login(request: LoginRequest, session: HttpSession): UserResponse {
        //전화번호로 사용자 조회
        val user = userRepository.findByPhone(request.phone)
            ?: throw IllegalArgumentException("전화번호 또는 비밀번호가 올바르지 않습니다.")
        //비번 검증
        if(!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("전화번호 또는 비밀번호가 올바르지 않습니다.")
        }
        //사용자 정보 저장
        session.setAttribute("userId", user.id)
        session.setAttribute("userPhone", user.phone)
        session.setAttribute("userRole", user.role.name)

        return UserResponse.from(user)
    }
    // 로그아웃
    fun logout(session: HttpSession) {
        session.invalidate()  // 세션 삭제
    }

    // 현재 로그인한 사용자 조회
    fun getCurrentUser(session: HttpSession): UserResponse? {
        val userId = session.getAttribute("userId") as? String
            ?: return null

        val user = userRepository.findById(userId).orElse(null)
            ?: return null

        return UserResponse.from(user)
    }

    //모든 사용자 조회
    fun findAll(): List<UserResponse> {
        return userRepository.findAll().map{ UserResponse.from(it) }
    }
}