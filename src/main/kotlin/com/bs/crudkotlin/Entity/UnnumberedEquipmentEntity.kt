package com.bs.crudkotlin.Entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "unnumbered_equipment")
class UnnumberedEquipmentEntity(
    @Id
    @Column(length = 36)
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, length = 30)
    var name: String,

    @Column(nullable = false)
    var stock: Int,

    @OneToMany(mappedBy = "equipment", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val reservations: MutableList<UnnumberedReservationEntity> = mutableListOf()
)