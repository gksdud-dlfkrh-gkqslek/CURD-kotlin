package com.bs.crudkotlin.Service

import com.bs.crudkotlin.Repository.HistoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HistoryCleanupService(
    private val historyRepository: HistoryRepository
) {
    @Transactional
    fun deleteOldHistories() {
        val oneYearBefore = LocalDate.now().minusYears(1)
        val historyList = historyRepository.findAll()

        for (history in historyList) {
            val date = history.reservedDate
            if (date != null && date <= oneYearBefore) {
                historyRepository.delete(history)
            }
        }
    }
}