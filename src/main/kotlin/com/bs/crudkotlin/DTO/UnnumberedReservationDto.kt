package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.UnnumberedEquipmentEntity
import com.bs.crudkotlin.Entity.UnnumberedReservationEntity
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import java.time.LocalDate
import java.util.UUID

data class UnnumberedReservationDto(

    val id: String? = null,

    @JsonFormat(pattern = "yyyy-MM-dd")
    val deadline: LocalDate?,

    @JsonFormat(pattern = "yyyy-MM-dd")
    val startdate: LocalDate? = null,

    val userId: String? = null,

    val returnStatus: String,

    val returnPending: Boolean = false
) {
    //DTO -> Entity
    fun toEntity() = UnnumberedReservationEntity(
        deadline = deadline,
        startdate = startdate,
        returnStatus = returnStatus
    )

    //Entity -> DTO
    companion object {
        fun fromEntity(entity: UnnumberedReservationEntity) = UnnumberedReservationDto(
            id = entity.id,
            deadline = entity.deadline,
            startdate = entity.startdate,
            userId = entity.userId,
            returnStatus = entity.returnStatus,
            returnPending = entity.returnPending
        )
    }
}