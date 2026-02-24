package com.bs.crudkotlin.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.processing.Pattern
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(length = 36)
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true)
    var phone: Long,

    @Column(nullable = false)
    var password: String,  // 암호화된 비밀번호 저장

    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

)
enum class UserRole {
    USER,   // 일반 사용자
    ADMIN   // 관리자
}