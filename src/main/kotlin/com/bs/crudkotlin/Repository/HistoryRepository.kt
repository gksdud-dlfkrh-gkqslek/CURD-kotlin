package com.bs.crudkotlin.Repository

import com.bs.crudkotlin.Entity.HistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository : JpaRepository<HistoryEntity, String> {
    fun findByUserId(userId: String): List<HistoryEntity>
}