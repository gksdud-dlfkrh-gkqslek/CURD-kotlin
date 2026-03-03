package com.bs.crudkotlin.Repository

import com.bs.crudkotlin.Entity.UnnumberedReservationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UnnumberedReservationRepository : JpaRepository<UnnumberedReservationEntity, String>