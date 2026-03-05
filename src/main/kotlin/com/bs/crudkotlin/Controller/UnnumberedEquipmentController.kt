package com.bs.crudkotlin.Controller

import com.bs.crudkotlin.DTO.ReserveRequest
import com.bs.crudkotlin.DTO.UnnumberedEquipmentDto
import com.bs.crudkotlin.DTO.UnnumberedReservationDto
import com.bs.crudkotlin.DTO.UnnumberedReserveRequest
import com.bs.crudkotlin.DTO.UnnumberedReturnRequest
import com.bs.crudkotlin.Service.UnnumberedEquipmentService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
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

    //번호 없는 장비 정보 수정
    @PutMapping("/stock/{id}")
    fun stockequipment(@PathVariable id:String, @RequestBody unnumberedEquipmentDto: UnnumberedEquipmentDto):ResponseEntity<String>{
        return unnumberedEquipmentService.stockequipment(id, unnumberedEquipmentDto)
    }

    //번호 없는 장비 예약
    @PostMapping("/reserve/{id}")
    fun unnumberedReserve(
        @PathVariable id: String,
        @RequestParam("deadline") deadline: String,
        @RequestParam("file", required = false) file: MultipartFile?,
        session: HttpSession
    ): ResponseEntity<String> {
        val request = UnnumberedReserveRequest(deadline = LocalDate.parse(deadline))    //반납일 요청
        return unnumberedEquipmentService.unnumberedReserve(id, request, session, file)
    }

    //번호 없는 장비 반납 요청
    @PutMapping("/return/{id}")
    fun unnumberedReturn(@PathVariable id:String, @RequestBody request: UnnumberedReturnRequest):ResponseEntity<String>{
        return unnumberedEquipmentService.unnumberedReturn(id,request.returnStatus)
    }

    //번호 없는 장비 반납 승인
    @PutMapping("/return/approve/{id}")
    fun unnumberedapprove(@PathVariable id: String): ResponseEntity<String>{
        return unnumberedEquipmentService.unnumberedapprove(id)
    }

    //번호 없는 장비 반납 대기 목록
    @GetMapping("/return/pending")
    fun unnumberedPending(): List<UnnumberedReservationDto> {
        return unnumberedEquipmentService.unnumberedPending()
    }

    //예약 취소
    @PutMapping("/cancel/{id}")
    fun unnumberedCancelled(@PathVariable id:String):ResponseEntity<String>{
        return unnumberedEquipmentService.unnumberedCancelled(id)
    }
}