package com.bs.crudkotlin.Repository

import com.bs.crudkotlin.Entity.EquipmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipmentRepository : JpaRepository<EquipmentEntity, String> {
    fun findByNum(num: Long): List<EquipmentEntity>
    fun findByName(name: String): List<EquipmentEntity>

    fun findByUserId(id: String): List<EquipmentEntity>
}