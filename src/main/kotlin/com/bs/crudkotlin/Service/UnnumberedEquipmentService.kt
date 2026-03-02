package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.DTO.UnnumberedEquipmentDto
import com.bs.crudkotlin.Entity.UnnumberedEquipmentEntity
import com.bs.crudkotlin.Repository.UnnumberedEquipmentRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class UnnumberedEquipmentService(
    private val unnumberedEquipmentRepository: UnnumberedEquipmentRepository,
) {
    fun unnumbercreate(unnumberedEquipmentDto: UnnumberedEquipmentDto):String{
        val entity: UnnumberedEquipmentEntity = unnumberedEquipmentDto.toEntity()
        unnumberedEquipmentRepository.save(entity)
        return "저장 완료"
    }
}