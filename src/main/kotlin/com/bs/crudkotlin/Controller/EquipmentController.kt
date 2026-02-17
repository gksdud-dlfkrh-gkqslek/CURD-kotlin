package com.bs.crudkotlin.Controller

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import com.bs.crudkotlin.Service.EquipmentService
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
@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/equipment")
class EquipmentController(val equipmentService: EquipmentService) {

    @GetMapping("/get")//전체 장비 조회
    fun getEquipment(): List<EquipmentDto> {
        return equipmentService.findAll()

    }
    @GetMapping("/get/num/{num}") // 단건 조회
    fun getByNum(@PathVariable num:Long): Any? {
        return equipmentService.findByNum(num)
    }
    @GetMapping("/get/name/{name}")
    fun getByName(@PathVariable name: String): Any? {
        return equipmentService.findByName(name)
    }

    @PostMapping("/post") // 장비 등록
    fun post(@RequestBody equipmentDto: EquipmentDto): ResponseEntity<Any> {
        return equipmentService.create(equipmentDto)
    }
    @PutMapping("/put/{id}") // 정보 수정
    fun put(@PathVariable id: String, @RequestBody equipmentDto: EquipmentDto): String{
        return equipmentService.update(id,equipmentDto)
    }
    @DeleteMapping("/delete/{id}") //장비 정보 삭제
    fun delete(@PathVariable id: String): Any?{
        return equipmentService.delete(id)
    }
}