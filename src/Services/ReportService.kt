package Services

import Data.TransactionInterface
import Models.MonthTransactions
import Models.Transaction
import Models.TransactionType

class ReportService(private val transactionInterface: TransactionInterface, private val reportValidatorInterface: ReportValidatorInterface) {

    fun getBalance(): Double {
        return calculateBalance(transactionInterface.getAll())
    }

    fun getSummaryByMonth(month: Int, year: Int): MonthTransactions {
        if (reportValidatorInterface.isValidMonth(month) && reportValidatorInterface.isValidYear(year)) {
            val transactions = transactionInterface.getByMonth(month, year)
            var totalIncome = 0.0
            var totalExpenses = 0.0
            transactions.forEach { transaction ->
                if (transaction.transactionType == TransactionType.INCOME)
                    totalIncome += transaction.amount
                else totalExpenses += transaction.amount
            }
            return MonthTransactions(month, year, totalIncome, totalExpenses, transactions)
        }
        return MonthTransactions(month, year, 0.0, 0.0, listOf())
    }

    private fun calculateBalance(transactions: List<Transaction>): Double {
        return transactions.fold(0.0) { total, transaction ->
            when (transaction.transactionType) {
                TransactionType.INCOME -> total + transaction.amount
                TransactionType.EXPENSES -> total - transaction.amount
            }
        }
    }
}
