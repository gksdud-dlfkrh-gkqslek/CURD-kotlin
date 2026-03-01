package com.bs.crudkotlin.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "history")
class HistoryEntity(
    @Id
    @Column(length = 36)
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val equipmentId: String,

    @Column(nullable = false)
    val equipmentName: String,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val userName: String,

    @Column(nullable = false)
    val userPhone: String,

    @Column(nullable = true)
    val reservedDate: LocalDate? = null,

    @Column(nullable = true)
    val deadline: LocalDate? = null,

    @Column(nullable = false)
    val returnDate: LocalDate = LocalDate.now(),

    @Column(nullable = false)
    val returnStatus: String
)