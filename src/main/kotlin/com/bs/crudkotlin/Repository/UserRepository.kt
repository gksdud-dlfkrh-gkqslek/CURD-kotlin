package com.bs.crudkotlin.Repository

import com.bs.crudkotlin.Entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByPhone(phone: Long): UserEntity?
    fun existsByPhone(phone: Long): Boolean // 중복 체크
}