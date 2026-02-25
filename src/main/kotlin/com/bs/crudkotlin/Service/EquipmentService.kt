package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.collections.map

@Service
class EquipmentService(private val equipmentRepository: EquipmentRepository) {

    // 전체 조회
    fun findAll(): List<EquipmentDto> {
        return equipmentRepository.findAll().map {
            EquipmentDto(
                it.id,
                it.num,
                it.name,
                it.status,
                it.reserved,
                it.deadline,
                it.startdate
            ) }
    }

    // num값으로 조회
    fun findByNum(num: Long): ResponseEntity<List<EquipmentDto>> {
        val entity = equipmentRepository.findByNum(num)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(entity.map{ EquipmentDto.fromEntity(it)})
    }

    // name값으로 조회
    fun findByName(name: String): ResponseEntity<List<EquipmentDto>> {
        val entity = equipmentRepository.findByName(name)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(entity.map{ EquipmentDto.fromEntity(it)})
    }

    // 장비 등록
    fun create(equipmentDto: EquipmentDto): ResponseEntity<Any> {
        val entity = equipmentDto.toEntity()
        val fromcreate = equipmentRepository.save(entity)
        val responseDto = EquipmentDto.fromEntity(fromcreate)
        return ResponseEntity.ok(responseDto)
    }

    // 예약
    fun updatereserve(id: String, equipmentDto: EquipmentDto): ResponseEntity<String> {
        val entity = equipmentRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        entity.reserved = true
        entity.deadline = equipmentDto.deadline
        entity.startdate = LocalDate.now()
        equipmentRepository.save(entity)
        return ResponseEntity.ok("예약 완료")
    }

    //예약 취소
    fun cancelReserve(id: String): ResponseEntity<String> {
        val entity = equipmentRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        entity.reserved = false
        entity.deadline = null
        entity.startdate = null
        equipmentRepository.save(entity)
        return ResponseEntity.ok("예약 취소 완료")
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