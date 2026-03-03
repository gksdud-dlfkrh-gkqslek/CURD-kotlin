package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.UnnumberedEquipmentEntity


data class UnnumberedEquipmentDto(
    val id: String? = null,
    val name: String,
    val stock: Int,
    val reservations: List<UnnumberedReservationDto> = emptyList()
) {
    // DTO -> Entity
    fun toEntity() = UnnumberedEquipmentEntity(
        name = name,
        stock = stock
    )

    //Entity -> DTO
    companion object {
        fun fromEntity(entity: UnnumberedEquipmentEntity) = UnnumberedEquipmentDto(
            id = entity.id,
            name = entity.name,
            stock = entity.stock,
            reservations = entity.reservations.map { UnnumberedReservationDto.fromEntity(it) }
        )
    }
}