package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EquipmentService(private val equipmentRepository: EquipmentRepository) {
    fun findAll(): List<EquipmentDto> {
        return equipmentRepository.findAll().map {
            EquipmentDto(
                it.num,
                it.name,
                it.status,
                it.deadline,
                it.startdate
            ) }
    }
    fun findByNum(num:Long): EquipmentEntity {
        return equipmentRepository.findByIdOrNull(num)?:throw IllegalArgumentException("장비 없음")
    }
    fun create(equipmentDto: EquipmentDto): EquipmentDto {
        val entity = equipmentDto.toEntity()
        val fromcreate = equipmentRepository.save(entity)
        return EquipmentDto.fromEntity(fromcreate)
    }
    fun update(): String{
        return "정보 수정"
    }
    fun delete(): String{
        return "장비 삭제"
    }
}