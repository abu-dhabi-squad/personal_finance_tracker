package Data

import Models.Transaction
import Models.UITransaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class InMemoryTransactionRepository : ITransactionRepository {
    private val transactions = mutableListOf<Transaction>()
    override fun add(transaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }

    override fun edit(id: UUID, Transaction: Transaction): Boolean {

        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val existingIndex = transactions.indexOfFirst { it.id == id }
        if (existingIndex == -1) throw NoSuchElementException("Transaction not found")
        val existing = transactions[existingIndex]

        // التحديث الجزئي
        val updatedTransaction = existing.copy(
            amount = Transaction.amount ?: existing.amount,
            category = Transaction.category ?: existing.category,
            transactionType =Transaction.transactionType ?: existing.transactionType,
            date = Transaction.date?.let { LocalDate.parse(it.toString(), dateFormatter) } ?: existing.date
        )

        transactions[existingIndex] = updatedTransaction
        return true
    }

    override fun delete(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Transaction> {
        TODO("Not yet implemented")
    }

    override fun getBalance(): Double {
        TODO("Not yet implemented")
    }

    override fun getByMonth(month: Int): List<Transaction> {
        TODO("Not yet implemented")
    }
}
