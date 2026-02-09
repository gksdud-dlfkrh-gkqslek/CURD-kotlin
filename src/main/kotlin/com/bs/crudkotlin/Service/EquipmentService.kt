package com.bs.crudkotlin.Service

import org.springframework.stereotype.Service

@Service
class EquipmentService {
    fun findAll(): String{
        return "장비 조회"
    }
    fun findByNum(): String{
        return "단건 장비 조회"
    }
    fun create(): String{
        return "장비 등록"
    }
    fun update(): String{
        return "정보 수정"
    }
    fun delete(): String{
        return "장비 삭제"
    }
}