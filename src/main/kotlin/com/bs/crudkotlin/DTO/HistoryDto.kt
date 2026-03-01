package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.HistoryEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class HistoryDto(
    val id: String,
    val equipmentId: String,
    val equipmentName: String,
    val userId: String,
    val userName: String,
    val userPhone: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val reservedDate: LocalDate? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val deadline: LocalDate? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val returnDate: LocalDate,
    val returnStatus: String
) {
    companion object {
        fun fromEntity(entity: HistoryEntity) = HistoryDto(
            id            = entity.id,
            equipmentId   = entity.equipmentId,
            equipmentName = entity.equipmentName,
            userId        = entity.userId,
            userName      = entity.userName,
            userPhone     = entity.userPhone,
            reservedDate  = entity.reservedDate,
            deadline      = entity.deadline,
            returnDate    = entity.returnDate,
            returnStatus  = entity.returnStatus
        )
    }
}