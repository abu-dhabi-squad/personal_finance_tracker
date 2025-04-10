package Services

import Data.TransactionInterface
import Models.MonthTransactions
import Models.TransactionType
import java.time.LocalDate

class ReportService(private val transactionInterface: TransactionInterface) {

    fun getBalance(): Double {
        val transactions = transactionInterface.getAll()
        var balance = 0.0
        transactions.forEach { transaction ->
            when (transaction.transactionType.name) {
                TransactionType.INCOME.toString() -> balance += transaction.amount
                TransactionType.EXPENSES.toString() -> balance -= transaction.amount
            }
        }
        return balance
    }

    fun getSummaryByMonth(month: Int, year: Int): MonthTransactions {
        if (month in 1..12 && year in 1900..LocalDate.now().year) {
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
}
