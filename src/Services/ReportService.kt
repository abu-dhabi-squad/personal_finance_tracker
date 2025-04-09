package Services

import Data.ITransactionRepository
import Models.Transaction
import Models.TransactionType

class ReportService(private val iTransactionRepository: ITransactionRepository) {

    fun getBalance(): Double {
        val transactions = iTransactionRepository.getAll()
        var balance = 0.0
        transactions.forEach { transaction ->
            when (transaction.transactionType.name) {
                TransactionType.INCOME.toString() -> balance += transaction.amount
                TransactionType.EXPENSES.toString() -> balance -= transaction.amount
            }
        }
        return balance
    }

    fun getSummaryByMonth(month: Int): List<Transaction> {
        if (month in 1..12) {
            val transactions = iTransactionRepository.getAll()
            return transactions.filter { transaction -> transaction.date.month.value == month }
        }
        return listOf()
    }
}
