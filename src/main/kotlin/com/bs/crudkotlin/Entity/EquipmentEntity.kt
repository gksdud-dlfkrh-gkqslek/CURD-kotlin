package com.bs.crudkotlin.Entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class EquipmentEntity protected constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val num: Long? = null

    @Column(nullable = false, length = 30)
    private var name: String? = null

    @Column(nullable = false, length = 30)
    private var status: String? = null

    @Column(length = 30)
    @ColumnDefault(" ")
    private var deadline: String? = null

    @CreationTimestamp
    var startdate: LocalDateTime? = null
}