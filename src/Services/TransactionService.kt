package Services

import Data.TransactionInterface
import Models.Transaction
import Models.UITransaction
import Utils.DateParserInterface
import java.util.*

class TransactionService(
    private val transactionData: TransactionInterface,
    private val validator: TransactionValidatorInterface,
    private val dateParser: DateParserInterface,
) {
    fun addTransaction(transaction: UITransaction): Boolean {
        if (!validator.isValidAmount(transaction.amount) || !validator.isValidDate(transaction.date)) return false
        val newTransaction = Transaction(
            amount = transaction.amount,
            transactionType = transaction.transactionType,
            category = transaction.category,
            date = dateParser.parseDateFromString(transaction.date),
        )
        return transactionData.add(newTransaction)
    }

    fun updateTransaction(id: UUID, uiTransaction: UITransaction): Boolean {
        if (!validator.isValidAmount(uiTransaction.amount) || !validator.isValidDate(uiTransaction.date)) return false
        val existingIndex = transactionData.getAll().indexOfFirst { it.id == id }
        if (existingIndex == -1) return false
        val existing = transactionData.getAll()[existingIndex]
        val updatedTransaction = existing.copy(
            amount = uiTransaction.amount,
            category = uiTransaction.category,
            transactionType = uiTransaction.transactionType,
            date = dateParser.parseDateFromString(uiTransaction.date)
        )
        return transactionData.edit(updatedTransaction)
    }


    fun deleteTransaction(transaction: Transaction): Boolean {
        return transactionData.delete(transaction)
    }

    fun listAllTransactions(): List<Transaction> {
        return transactionData.getAll()
    }
}
