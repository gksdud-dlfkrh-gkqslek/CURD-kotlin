package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.EquipmentEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class EquipmentDto(
    val num:Long?,
    val name:String,
    val status:String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val deadline:LocalDate?,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val startdate: LocalDate?
){  // Dto -> Entity 변환
    fun toEntity() = EquipmentEntity(
        num=num,
        name=name,
        status=status,
        deadline=deadline
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