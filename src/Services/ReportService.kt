package Services

import Data.TransactionInterface
import Models.Category
import Models.SummaryTransactions
import Models.TransactionType

class ReportService(private val transactionInterface: TransactionInterface, private val reportValidatorInterface: ReportValidatorInterface) {

    fun getBalance(): Double {
        val transactions = transactionInterface.getAll()
        var balance = 0.0
        transactions.forEach { transaction ->
            when (transaction.transactionType) {
                TransactionType.INCOME -> balance += transaction.amount
                TransactionType.EXPENSES -> balance -= transaction.amount
            }
        }
        return balance
    }

    fun getSummaryByMonth(month: Int, year: Int): SummaryTransactions {
        if (reportValidatorInterface.isValidMonth(month) && reportValidatorInterface.isValidYear(year)) {
            val transactions = transactionInterface.getByMonth(month, year)
            var totalIncome = 0.0
            var totalExpenses = 0.0
            transactions.forEach { transaction ->
                if (transaction.transactionType == TransactionType.INCOME)
                    totalIncome += transaction.amount
                else totalExpenses += transaction.amount
            }
            return SummaryTransactions(totalIncome, totalExpenses, transactions)
        }
        return SummaryTransactions(0.0, 0.0, listOf())
    }

    fun getSummaryByCategory(category: Category): SummaryTransactions {
        if (reportValidatorInterface.isValidcategory(category)) {
            val transactions = transactionInterface.getAll()
            var totalIncome = 0.0
            var totalExpenses = 0.0

            val transactionsRes = transactions.filter { transaction -> transaction.category.name == category.name }

            transactionsRes.forEach { transaction ->
                if (transaction.transactionType == TransactionType.INCOME)
                    totalIncome += transaction.amount
                else totalExpenses += transaction.amount
            }

            return SummaryTransactions( totalIncome, totalExpenses, transactionsRes)
        }
        return SummaryTransactions(0.0, 0.0, listOf())
    }

    fun getSummaryByType(transactionType: TransactionType): SummaryTransactions {
        if (reportValidatorInterface.isValidTransactionType(transactionType)) {
            val transactions = transactionInterface.getAll()
            var totalIncome = 0.0
            var totalExpenses = 0.0

            val transactionsRes = transactions.filter { transaction -> transaction.transactionType.name == transactionType.name }

            transactionsRes.forEach { transaction ->
                if (transaction.transactionType == TransactionType.INCOME)
                    totalIncome += transaction.amount
                else totalExpenses += transaction.amount
            }

            return SummaryTransactions( totalIncome, totalExpenses, transactionsRes)
        }
        return SummaryTransactions(0.0, 0.0, listOf())
    }


}
