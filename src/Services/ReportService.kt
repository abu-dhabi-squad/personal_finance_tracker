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
        if (!reportValidatorInterface.isValidMonth(month) || !reportValidatorInterface.isValidYear(year))
            return MonthTransactions(month, year, 0.0, 0.0, emptyList())

        val transactions = transactionInterface.getByMonth(month, year)
        val totalIncome = calculateTotalByType(transactions, TransactionType.INCOME)
        val totalExpenses = calculateTotalByType(transactions, TransactionType.EXPENSES)

        return MonthTransactions(month, year, totalIncome, totalExpenses, transactions)

    }

    private fun calculateBalance(transactions: List<Transaction>): Double {
        return transactions.fold(0.0) { total, transaction ->
            when (transaction.transactionType) {
                TransactionType.INCOME -> total + transaction.amount
                TransactionType.EXPENSES -> total - transaction.amount
            }
        }
    }

    private fun calculateTotalByType(transactions: List<Transaction>, type: TransactionType): Double {
        return transactions
            .filter { it.transactionType == type }
            .sumOf { it.amount }
    }

}
