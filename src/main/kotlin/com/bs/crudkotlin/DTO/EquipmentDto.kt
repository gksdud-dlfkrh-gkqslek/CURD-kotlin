package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.EquipmentEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class EquipmentDto(

    val id: String? = null,
    val num: Long,
    val name: String,
    val status: String,
    val reserved: Boolean = false,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val startdate: LocalDate? = null

) {

    // DTO -> Entity
    fun toEntity() = EquipmentEntity(
        num = num,
        name = name,
        status = status
    )

    //Entity -> DTO
    companion object {
        fun fromEntity(entity: EquipmentEntity) = EquipmentDto(
            id = entity.id,
            num = entity.num,
            name = entity.name,
            status = entity.status,
            reserved = entity.reserved,
            startdate = entity.startdate
        )
    }
}
data class ReserveRequest(
    @JsonFormat(pattern = "yyyy-MM-dd")
    val deadline: LocalDate
)
