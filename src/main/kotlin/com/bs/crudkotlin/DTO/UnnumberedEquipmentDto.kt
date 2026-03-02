package com.bs.crudkotlin.DTO

import com.bs.crudkotlin.Entity.UnnumberedEquipmentEntity


data class UnnumberedEquipmentDto(
    val id: String? = null,
    val name: String,
    val stock: Int,
    val status: String
) {
    // DTO -> Entity
    fun toEntity() = UnnumberedEquipmentDto(
        name = name,
        stock = stock,
        status = status
    )

    //Entity -> DTO
    companion object {
        fun fromEntity(entity: UnnumberedEquipmentEntity) = UnnumberedEquipmentDto(
            id = entity.id,
            name = entity.name,
            stock = entity.stok,
            status = entity.status
        )
    }
}