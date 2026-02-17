package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class EquipmentService(private val equipmentRepository: EquipmentRepository) {
    fun findAll(): List<EquipmentDto> {
        return equipmentRepository.findAll().map {
            EquipmentDto(
                it.id,
                it.num,
                it.name,
                it.status,
                it.deadline,
                it.startdate
            ) }
    }
    fun findByNum(num:Long): Any? {
        val errorcheck: EquipmentEntity? = equipmentRepository.findById(num).orElse(null)
        if(errorcheck == null){
            return ResponseEntity.status(404).body("$num 번 장비를 찾을 수 없습니다")
        }
        return errorcheck
    }
    fun create(equipmentDto: EquipmentDto): String {
        val entity = equipmentDto.toEntity()
        val fromcreate = equipmentRepository.save(entity)
        EquipmentDto.fromEntity(fromcreate)
        return "등록 완료!"
    }
    fun update(num:Long,equipmentDto: EquipmentDto): String{
        val entity = equipmentRepository.findById(num).get()
        entity.num = equipmentDto.num
        entity.name = equipmentDto.name
        entity.status = equipmentDto.status
        entity.deadline = equipmentDto.deadline
        equipmentRepository.save(entity)
        return "정보 수정 완료"
    }
    fun delete(num:Long): Any?{
        val errorcheck: EquipmentEntity? = equipmentRepository.findById(num).orElse(null)
        if(errorcheck == null){
            return ResponseEntity.status(404).body("$num 번 장비를 찾을 수 없습니다")
        }
        equipmentRepository.deleteById(num)
        return "장비 삭제 완료"
    }
}