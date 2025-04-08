package Models

import java.time.LocalDate
import java.util.UUID

data class Transaction(
    val amount: Double,
    val category: Category,
    val transactionType: TransactionType,
    val date: LocalDate,
    val id: UUID = UUID.randomUUID()
)