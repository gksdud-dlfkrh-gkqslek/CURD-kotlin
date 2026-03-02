package com.bs.crudkotlin.Controller

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.DTO.UnnumberedEquipmentDto
import com.bs.crudkotlin.Service.UnnumberedEquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/unnumbered")
class UnnumberedEquipmentController(
    private val unnumberedEquipmentService: UnnumberedEquipmentService
) {
    //번호 없는 장비 등록
    @PostMapping
    fun unnumbercreate(@RequestBody unnumberedEquipmentDto: UnnumberedEquipmentDto):String{
        return unnumberedEquipmentService.unnumbercreate(unnumberedEquipmentDto)
    }

    //번호 없는 장비 삭제
    @DeleteMapping("/{id}")
    fun unnumbereddelete(@PathVariable id:String):String{
        return unnumberedEquipmentService.unnumberdelete(id)
    }

    //번호 없는 장비 조회
    @GetMapping
    fun unnumberedEquipment(): List<UnnumberedEquipmentDto> {
        return unnumberedEquipmentService.unnumberedEquipment()
    }

    //번호 없는 장비 재고 수정
    //번호 없는 장비 예약
    //번호 없는 장비 반납 요청
    //번호 없는 장비 반납 승인
    //번호 없는 장비 반납 대기 목록
}