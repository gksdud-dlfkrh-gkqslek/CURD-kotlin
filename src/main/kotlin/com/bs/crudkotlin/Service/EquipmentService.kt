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

        // 전체 조회
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

    // num값으로 조회
    fun findByNum(num: Long): ResponseEntity<EquipmentDto> {
        val entity = equipmentRepository.findByNum(num)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(EquipmentDto.fromEntity(entity))
    }

    // name값으로 조회
    fun findByName(name: String): ResponseEntity<EquipmentDto> {
        val entity = equipmentRepository.findByName(name)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(EquipmentDto.fromEntity(entity))
    }

    // 장비 등록
    fun create(equipmentDto: EquipmentDto): ResponseEntity<Any> {
        val entity = equipmentDto.toEntity()
        val fromcreate = equipmentRepository.save(entity)
        val responseDto = EquipmentDto.fromEntity(fromcreate)
        return ResponseEntity.ok(responseDto)
    }

    //장비 수정
    fun update(id: String, equipmentDto: EquipmentDto): ResponseEntity<String>{
        val entity = equipmentRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        entity.num = equipmentDto.num
        entity.name = equipmentDto.name
        entity.status = equipmentDto.status
        entity.deadline = equipmentDto.deadline
        equipmentRepository.save(entity)
        return ResponseEntity.ok("정보 수정 완료")
    }

    // 장비 삭제
    fun delete(id: String): String? {
        val errorcheck: EquipmentEntity? = equipmentRepository.findById(id).orElse(null)

        equipmentRepository.deleteById(id)
        return "장비 삭제 완료"
    }
}