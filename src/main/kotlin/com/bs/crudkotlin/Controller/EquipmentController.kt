package com.bs.crudkotlin.Controller

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.DTO.ReserveRequest
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import com.bs.crudkotlin.Service.EquipmentService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping("/api/equipments")
class EquipmentController(val equipmentService: EquipmentService) {

    // 전체 장비 조회
    @GetMapping()
    fun getEquipment(): List<EquipmentDto> {
        return equipmentService.findAll()
    }

    // num값으로 조회
    @GetMapping("/num/{num}")
    fun getByNum(@PathVariable num:Long): ResponseEntity<List<EquipmentDto>> {
        return equipmentService.findByNum(num)
    }

    // name값으로 조회
    @GetMapping("/name/{name}")
    fun getByName(@PathVariable name: String): ResponseEntity<List<EquipmentDto>> {
        return equipmentService.findByName(name)
    }

    // 장비 등록
    @PostMapping()
    fun post(@RequestBody equipmentDto: EquipmentDto): ResponseEntity<Any> {
        return equipmentService.create(equipmentDto)
    }

    // 예약
    @PutMapping("/reserve/{id}")
    fun putreserve(@PathVariable id: String, @RequestBody request: ReserveRequest, session: HttpSession): ResponseEntity<String> {
        return equipmentService.updatereserve(id, request,session)
    }

    //예약 취소
    @PutMapping("/cancel/{id}")
    fun cancelReserve(@PathVariable id: String): ResponseEntity<String> {
        return equipmentService.cancelReserve(id)
    }

    // 정보 수정
    @PutMapping("/{id}")
    fun put(@PathVariable id: String, @RequestBody equipmentDto: EquipmentDto): ResponseEntity<String>{
        return equipmentService.update(id,equipmentDto)
    }

    // 장비 정보 삭제
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): String? {
        return equipmentService.delete(id)
    }
}