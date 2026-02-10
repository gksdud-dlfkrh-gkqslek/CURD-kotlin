package com.bs.crudkotlin.Entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class EquipmentEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var num: Long? = null,

    @Column(nullable = false, length = 30)
    var name: String,

    @Column(nullable = false, length = 30)
    var status: String,

    @Column(length = 30)
    @ColumnDefault(" ")
    var deadline: String? = null,

    @CreationTimestamp
    var startdate: LocalDateTime? = null

)
