package com.bs.crudkotlin.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "unnumbered_reservation")
class UnnumberedReservationEntity (
    @Id
    @Column(length = 36)
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = true)
    var deadline: LocalDate? = null,

    @Column(nullable = true)
    var startdate: LocalDate? = null,

    @Column(nullable = true)
    var userId: String? = null,

    @Column(nullable = true, length = 30)
    var returnStatus: String = "Unnumbered",

    @Column(nullable = false)
    var returnPending: Boolean = false,

    @Column(nullable = false, length = 30)
    var reserved: Boolean = false,

    @Column(nullable = true, length = 500)
    var filename: String? = null,

    @Column(nullable = true, length = 500)
    var filePath: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    var equipment: UnnumberedEquipmentEntity
)