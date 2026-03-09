package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.EquipmentDto
import com.bs.crudkotlin.DTO.HistoryDto
import com.bs.crudkotlin.DTO.ReserveRequest
import com.bs.crudkotlin.Entity.EquipmentEntity
import com.bs.crudkotlin.Entity.HistoryEntity
import com.bs.crudkotlin.Repository.EquipmentRepository
import com.bs.crudkotlin.Repository.HistoryRepository
import com.bs.crudkotlin.Repository.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import java.util.UUID
import kotlin.collections.map

@Service
@EnableScheduling
class EquipmentService(
    private val equipmentRepository: EquipmentRepository,
    private val historyRepository: HistoryRepository,
    private val userRepository: UserRepository,
    private val historyCleanupService: HistoryCleanupService
) {

    // 전체 조회
    fun findAll(): List<EquipmentDto> {

        return equipmentRepository.findAll().map {
            EquipmentDto.fromEntity(it) }
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
    fun create(equipmentDto: EquipmentDto,file: MultipartFile?): ResponseEntity<Any> {
        val entity = equipmentDto.toEntity()

        var savedFileName: String? = null
        var savedFilePath: String? = null

        if (file != null && !file.isEmpty) {
            val uploadDir = File(System.getProperty("user.dir"),"uploads")
            if (!uploadDir.exists()){
                uploadDir.mkdirs()
            }

            val fileName = "${UUID.randomUUID()}_${file.originalFilename}"  // 파일 충돌 방지
            val destFile = File(uploadDir, fileName)

            file.transferTo(destFile)

            savedFileName = fileName
            savedFilePath = destFile.absolutePath
        }

        entity.filename = savedFileName
        entity.filePath = savedFilePath

        val fromcreate = equipmentRepository.save(entity)
        val responseDto = EquipmentDto.fromEntity(fromcreate)
        return ResponseEntity.ok(responseDto)
    }

    // 예약
    fun updatereserve(id: String, request: ReserveRequest, session: HttpSession): ResponseEntity<String> {
        val entity = equipmentRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        val userId = session.getAttribute("userId") as String?
        entity.reserved = true
        entity.deadline = request.deadline
        entity.startdate = LocalDate.now()
        entity.userId = userId
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
        entity.filename = equipmentDto.filename
        entity.filePath = equipmentDto.filePath
        equipmentRepository.save(entity)
        return ResponseEntity.ok("정보 수정 완료")
    }

    // 장비 삭제
    fun delete(id: String): String? {
        val errorcheck: EquipmentEntity? = equipmentRepository.findById(id).orElse(null)

        equipmentRepository.deleteById(id)
        return "장비 삭제 완료"
    }

    //반납 요청
    fun requestReturn(id: String, returnStatus: String): ResponseEntity<String> {
        val entity = equipmentRepository.findById(id).orElse(null)
        ?: return ResponseEntity.notFound().build()
        entity.status = returnStatus
        entity.returnPending = true
        equipmentRepository.save(entity)
        return ResponseEntity.ok("반납 요청 완료")
    }

    //반납 요청 승인
    fun approveReturn(id: String): ResponseEntity<String> {
        val entity = equipmentRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        val user = entity.userId?.let { userRepository.findById(it).orElse(null) }

        if (user != null) {
            historyRepository.save(
                HistoryEntity(
                    equipmentId = entity.id,
                    equipmentName = entity.name,
                    num = entity.num,
                    userId = user.id,
                    userName = user.name,
                    userPhone = user.phone,
                    reservedDate = entity.startdate,
                    deadline = entity.deadline,
                    returnDate = LocalDate.now(),
                    returnStatus = entity.status.replace("요청", ""),
                    filename = entity.filename,
                    filePath = entity.filePath
                )
            )
        }

        entity.status        = entity.status.replace("요청", "")
        entity.reserved      = false
        entity.returnPending = false
        entity.startdate     = null
        entity.deadline      = null
        entity.userId        = null
        equipmentRepository.save(entity)
        return ResponseEntity.ok("반납 승인 완료")
    }


    // 반납 대기 목록
    fun findReturnPending(): List<EquipmentDto> {
        return equipmentRepository.findByReturnPending(true).map { EquipmentDto.fromEntity(it) }
    }

    // 전체 히스토리 조회 (관리자)
    fun gethistory(): List<HistoryDto> {
        val historyList = historyRepository.findAll()
        return historyList.map { HistoryDto.fromEntity(it) }
    }

    // 내 히스토리 조회 (유저)
    fun getmyhistory(session: HttpSession): List<HistoryDto> {
        val userId = session.getAttribute("userId") as? String
            ?: return emptyList()
        val historyList = historyRepository.findByUserId(userId)
        return historyList.map { HistoryDto.fromEntity(it) }
    }

    //1년 지난 히스토리 자동 삭제
    @Scheduled(fixedRate = 10000)
    fun deleteOldHistory() {
        historyCleanupService.deleteOldHistories()
    }

}