package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(private val equipmentRepository: EquipmentRepository) {
    fun findAll(): String{
        return "장비 조회"
    }
    fun findByNum(): String{
        return "단건 장비 조회"
    }
    fun create(equipmentDto: EquipmentDto): EquipmentEntity? {
        val entity = equipmentDto.toEntity()
        return equipmentRepository.save(entity)
    }
    fun update(): String{
        return "정보 수정"
    }
    fun delete(): String{
        return "장비 삭제"
    }
}