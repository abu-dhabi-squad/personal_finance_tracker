package Services

import Data.TransactionInterface
import Data.InMemoryTransactionImplementation
import Models.Transaction
import Models.UITransaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TransactionService(
    private val transactions: MutableList<Transaction> = mutableListOf(),
    private val transactionRepo: TransactionInterface = InMemoryTransactionImplementation(
        transactions = transactions,
    ),
) {
    fun addTransaction(transaction: UITransaction): Boolean {
        val validator = TransactionValidatorImplementation()
        if (!validator.isValidAmount(transaction.amount) || !validator.isValidDate(transaction.date)) return false
        val newTransaction = Transaction(
            amount = transaction.amount,
            transactionType = transaction.transactionType,
            category = transaction.category,
            date = LocalDate.parse(transaction.date, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
        )
        return transactionRepo.add(newTransaction)
    }

    fun updateTransaction(id: UUID, uiTransaction: UITransaction): Boolean {
        val validator = TransactionValidatorImplementation()
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        if (!validator.isValidAmount(uiTransaction.amount) || !validator.isValidDate(uiTransaction.date)) return false
        val existingIndex = transactionRepo.getAll().indexOfFirst { it.id == id }
        if (existingIndex == -1) return false
        val existing = transactionRepo.getAll()[existingIndex]

        val updatedTransaction = existing.copy(
            amount = uiTransaction.amount,
            category = uiTransaction.category,
            transactionType = uiTransaction.transactionType,
            date = uiTransaction.date.let { LocalDate.parse(it, dateFormatter) } ?: existing.date
        )
        return transactionRepo.edit(updatedTransaction)
    }


    fun deleteTransaction(transaction: Transaction): Boolean {
        return transactionRepo.delete(transaction)
    }

    fun listAllTransactions(): List<Transaction> {
        return transactionRepo.getAll()
    }
}
