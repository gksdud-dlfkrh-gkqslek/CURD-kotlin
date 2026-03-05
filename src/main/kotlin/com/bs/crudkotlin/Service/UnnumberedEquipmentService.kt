package com.bs.crudkotlin.Service

import com.bs.crudkotlin.DTO.ReserveRequest
import com.bs.crudkotlin.DTO.UnnumberedEquipmentDto
import com.bs.crudkotlin.DTO.UnnumberedReservationDto
import com.bs.crudkotlin.DTO.UnnumberedReserveRequest
import com.bs.crudkotlin.Entity.HistoryEntity
import com.bs.crudkotlin.Entity.UnnumberedEquipmentEntity
import com.bs.crudkotlin.Entity.UnnumberedReservationEntity
import com.bs.crudkotlin.Repository.HistoryRepository
import com.bs.crudkotlin.Repository.UnnumberedEquipmentRepository
import com.bs.crudkotlin.Repository.UnnumberedReservationRepository
import com.bs.crudkotlin.Repository.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import java.util.UUID

@Service
class UnnumberedEquipmentService(
    private val unnumberedEquipmentRepository: UnnumberedEquipmentRepository,
    private val unnumberedReservationRepository: UnnumberedReservationRepository,
    private val userRepository: UserRepository,
    private val historyRepository: HistoryRepository
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

    //번호 없는 장비 조회
    fun unnumberedEquipment(): List<UnnumberedEquipmentDto>{
        return unnumberedEquipmentRepository.findAll().map {
            UnnumberedEquipmentDto.fromEntity(it)
        }
    }

    //번호 없는 장비 정보 수정
    fun stockequipment(id: String,unnumberedEquipmentDto: UnnumberedEquipmentDto):ResponseEntity<String>{
        val entity = unnumberedEquipmentRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        entity.name = unnumberedEquipmentDto.name
        entity.stock = unnumberedEquipmentDto.stock
        unnumberedEquipmentRepository.save(entity)
        return ResponseEntity.ok("수정 완료")
    }

    //번호 없는 장비 예약
    fun unnumberedReserve(id: String, request: UnnumberedReserveRequest, session: HttpSession, file: MultipartFile?):ResponseEntity<String>{
        val entity = unnumberedEquipmentRepository.findById(id).orElse(null)
        ?: return ResponseEntity.notFound().build()

        if(entity.stock>0){
            entity.stock--
            unnumberedEquipmentRepository.save(entity)

            val userId = session.getAttribute("userId") as? String
            ?: return ResponseEntity.status(401).body("로그인해라")

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

            val reservation = UnnumberedReservationEntity(
                deadline = request.deadline,
                startdate = LocalDate.now(),
                userId = userId,
                equipment = entity,
                reserved = true,
                filename = savedFileName,
                filePath = savedFilePath
            )

            unnumberedReservationRepository.save(reservation)
            return ResponseEntity.ok("예약 완료")
        }
        else{
            return ResponseEntity.ok("재고 없음")
        }
    }

    //번호 없는 장비 반납 요청
    fun unnumberedReturn(id:String, returnStatus: String):ResponseEntity<String>{
        val entity = unnumberedReservationRepository.findById(id).orElse(null)
        ?: return ResponseEntity.notFound().build()

        entity.returnStatus = returnStatus
        entity.returnPending = true

        unnumberedReservationRepository.save(entity)
        return ResponseEntity.ok("반납 요청 완료")

    }

    //번호 없는 장비 반납 승인
    fun unnumberedapprove(id: String):ResponseEntity<String>{
        val reserverved = unnumberedReservationRepository.findById(id).orElse(null)
        ?: return ResponseEntity.notFound().build()

        val entity = reserverved.equipment

        val user = reserverved.userId?.let { userRepository.findById(it).orElse(null) }

        if (user != null) {
            historyRepository.save(
                HistoryEntity(
                    equipmentId = entity.id,
                    equipmentName = entity.name,
                    num = null,
                    userId = user.id,
                    userName = user.name,
                    userPhone = user.phone,
                    reservedDate = reserverved.startdate,
                    deadline = reserverved.deadline,
                    returnDate = LocalDate.now(),
                    returnStatus = reserverved.returnStatus.replace("요청", ""),
                    filename = reserverved.filename
                )
            )
        }

        unnumberedReservationRepository.deleteById(reserverved.id)

        entity.stock ++
        unnumberedEquipmentRepository.save(entity)

        return ResponseEntity.ok("반납 승인 완료")
    }

    //번호 없는 장비 반납 대기 목록
    fun unnumberedPending(): List<UnnumberedReservationDto>{
        return unnumberedReservationRepository.findByReturnPending(true).map {
            UnnumberedReservationDto.fromEntity(it)
        }
    }

    //예약 취소
    fun unnumberedCancelled(id: String):ResponseEntity<String>{
        val reserverved = unnumberedReservationRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        val entity = reserverved.equipment

        val user = reserverved.userId?.let { userRepository.findById(it).orElse(null) }

        unnumberedReservationRepository.deleteById(reserverved.id)

        entity.stock ++
        unnumberedEquipmentRepository.save(entity)

        return ResponseEntity.ok("예약 취소 완료")
    }
}