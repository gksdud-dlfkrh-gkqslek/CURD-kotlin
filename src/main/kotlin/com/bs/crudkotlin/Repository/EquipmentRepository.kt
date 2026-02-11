package com.bs.crudkotlin.Repository

import com.bs.crudkotlin.Entity.EquipmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipmentRepository : JpaRepository<EquipmentEntity, Long> {
    fun findByNum(num: Long): EquipmentEntity?
}