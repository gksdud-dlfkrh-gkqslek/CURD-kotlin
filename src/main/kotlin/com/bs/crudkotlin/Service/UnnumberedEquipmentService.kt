package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.DTO.UnnumberedEquipmentDto
import com.bs.crudkotlin.Entity.UnnumberedEquipmentEntity
import com.bs.crudkotlin.Repository.UnnumberedEquipmentRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class UnnumberedEquipmentService(
    private val unnumberedEquipmentRepository: UnnumberedEquipmentRepository,
) {
    //번호 없는 장비 등록
    fun unnumbercreate(unnumberedEquipmentDto: UnnumberedEquipmentDto):String{
        val entity: UnnumberedEquipmentEntity = unnumberedEquipmentDto.toEntity()
        unnumberedEquipmentRepository.save(entity)
        return "저장 완료"
    }

    //번호 없는 장비 삭제
    fun unnumberdelete(id:String):String{
        val entity: UnnumberedEquipmentEntity = unnumberedEquipmentRepository.findById(id).orElse(null)
        unnumberedEquipmentRepository.delete(entity)
        return "삭제 완료"
    }

}