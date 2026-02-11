package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.EquipmentEntity
import java.time.LocalDate
import java.time.LocalDateTime

data class EquipmentDto(
    val num:Long,
    val name:String,
    val status:String,
    val deadline:LocalDate,
    val startdate: LocalDate
){  // Dto -> Entity 변환
    fun toEntity() = EquipmentEntity(
        num=num,
        name=name,
        status=status,
        deadline=deadline,
        startdate=startdate
    )
    // Entity -> Dto 변환
    companion object {
        fun fromEntity(entity: EquipmentEntity) = EquipmentDto(
            num=entity.num,
            name=entity.name,
            status=entity.status,
            deadline=entity.deadline,
            startdate=entity.startdate

        )
    }
}