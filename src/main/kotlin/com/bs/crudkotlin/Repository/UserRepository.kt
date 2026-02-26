package com.bs.crudkotlin.Repository

import com.bs.crudkotlin.Entity.ApprovalStatus
import com.bs.crudkotlin.Entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByPhone(phone: String): UserEntity?
    fun existsByPhone(phone: String): Boolean // 중복 체크
    fun findByApprovalStatus(status: ApprovalStatus): List<UserEntity> // 승인 대기 목록 조회
}