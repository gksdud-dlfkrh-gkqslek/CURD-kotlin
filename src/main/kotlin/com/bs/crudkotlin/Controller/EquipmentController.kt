package com.bs.crudkotlin.Controller

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import com.bs.crudkotlin.Service.EquipmentService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/equipment")
class EquipmentController(val equipmentService: EquipmentService) {

    @GetMapping("/get")//전체 장비 조회
    fun getEquipment(): List<EquipmentDto> {
        return equipmentService.findAll()

    }
    @GetMapping("/get1/{num}") // 단건 조회
    fun get1(@PathVariable num:Long): EquipmentEntity {
        return equipmentService.findByNum(num)
    }
    @PostMapping("/post") // 장비 등록
    fun post(@RequestBody equipmentDto: EquipmentDto): EquipmentDto {
        return equipmentService.create(equipmentDto)
    }
    @PutMapping("/put") // 정보 수정
    fun put(): String{
        return equipmentService.update()
    }
    @DeleteMapping("/delete") //장비 정보 삭제
    fun delete(): String{
        return equipmentService.delete()
    }
}