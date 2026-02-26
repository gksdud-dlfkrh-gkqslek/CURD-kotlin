package com.bs.crudkotlin.Entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "equipment_data")
@EntityListeners(AuditingEntityListener::class)
class EquipmentEntity(

    @Id
    @Column(length = 36)
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, length = 30)
    var num: Long = 0,

    @Column(nullable = false, length = 30)
    var name: String,

    @Column(nullable = false, length = 30)
    var status: String,

    @Column(nullable = false, length = 30)
    var reserved: Boolean = false,

    @Column(nullable = true)
    var deadline: LocalDate? = null,

    @Column(nullable = true)
    var startdate: LocalDate? = null
)