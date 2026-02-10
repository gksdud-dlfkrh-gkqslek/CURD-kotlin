package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.EquipmentEntity
import java.time.LocalDateTime

data class EquipmentDto(
    val num:Long,
    val name:String,
    val status:String,
    val deadline:String,
    val startdate:LocalDateTime
){  // Dto -> Entity 변환
    fun toEntity() = EquipmentEntity(
        num=num,
        name=name,
        status=status,
        deadline=deadline,
        startdate=startdate
    )
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