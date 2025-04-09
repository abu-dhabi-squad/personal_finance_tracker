package Data

import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class InMemoryTransactionRepository(
    private val transactions: MutableList<Transaction> = mutableListOf(),
    private var balance: Double = 0.0,
) : ITransactionRepository {

    override fun add(transaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }

    override fun edit(transaction: Transaction): Boolean {
//        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//        val existingIndex = transactions.indexOfFirst { it.id == id }
//        if (existingIndex == -1) throw NoSuchElementException("Transaction not found")
//        val existing = transactions[existingIndex]
//
//        // التحديث الجزئي
//        val updatedTransaction = existing.copy(
//            amount = Transaction.amount ?: existing.amount,
//            category = Transaction.category ?: existing.category,
//            transactionType =Transaction.transactionType ?: existing.transactionType,
//            date = Transaction.date?.let { LocalDate.parse(it.toString(), dateFormatter) } ?: existing.date
//        )
//
//        transactions[existingIndex] = updatedTransaction
        return true
    }

    override fun delete(transaction: Transaction): Boolean {
        if (transactions.contains(transaction)) {
            if (transaction.transactionType == TransactionType.INCOME && !canWithdraw(transaction.amount)) return false
            val success = transactions.remove(transaction)
            if (success) {
                when(transaction.transactionType) {
                    TransactionType.INCOME -> balance -= transaction.amount
                    TransactionType.EXPENSES -> balance += transaction.amount
                }
                return true
            }
        }
        return false
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

    private fun canWithdraw(amount: Double): Boolean {
        return amount <= balance
    }
}
