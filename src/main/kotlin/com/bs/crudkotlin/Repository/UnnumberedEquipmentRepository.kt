package com.bs.crudkotlin.Repository

import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Entity.UnnumberedEquipmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UnnumberedEquipmentRepository : JpaRepository<UnnumberedEquipmentEntity, String> {
}