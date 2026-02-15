package com.bs.crudkotlin.Entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "equipment_data")
class EquipmentEntity(

    @Id
    var num: Long? = null,

    @Column(nullable = false, length = 30)
    var name: String,

    @Column(nullable = false, length = 30)
    var status: String,

    @Column(nullable = false)
    var deadline: LocalDate? = null,

)
