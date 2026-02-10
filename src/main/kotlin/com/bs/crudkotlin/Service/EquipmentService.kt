package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.Entity.EquipmentEntity
import org.springframework.stereotype.Service

@Service
class EquipmentService {
    // DTO -> Entity
    fun equipmentDtoToEntity(dto: EquipmentDto): EquipmentEntity {}
    fun findAll(): String{
        return "장비 조회"
    }
    fun findByNum(): String{
        return "단건 장비 조회"
    }
    fun create(equipmentDto: EquipmentDto): String{
        val entity = dto.
    }
    fun update(): String{
        return "정보 수정"
    }
    fun delete(): String{
        return "장비 삭제"
    }
}