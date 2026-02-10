package com.bs.crudkotlin.Entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class EquipmentEntity(num: Long, name: String, status: String, deadline: String, startdate: LocalDateTime) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val num: Long? = null

    @Column(nullable = false, length = 30)
    private val name: String? = null

    @Column(nullable = false, length = 30)
    private val status: String? = null

    @Column(length = 30)
    @ColumnDefault(" ")
    private val deadline: String? = null

    @CreationTimestamp
    val startdate: LocalDateTime? = null
}