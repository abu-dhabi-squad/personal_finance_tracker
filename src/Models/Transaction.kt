package Models

import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

data class Transaction(
    val amount: Double,
    val category: Category,
    val transactionType: TransactionType,
    val date: LocalDate,
    val id: UUID = UUID.randomUUID()
) : Serializable