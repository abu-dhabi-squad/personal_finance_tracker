package Services

import Models.Category
import Models.Transaction
import Models.TransactionType
import java.time.LocalDate

class TransactionService(
    private val transactions: MutableList<Transaction> = mutableListOf()
) {
    private val balance: Double = 1000.0

    fun getBalance(): Double {
        return balance
    }

    fun getByMonth(month: Int): List<Transaction> {
        return listOf(
            Transaction(
                amount = 100.0,
                date = LocalDate.now(),
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            ),
            Transaction(
                amount = 200.0,
                date = LocalDate.now(),
                category = Category("Test 1"),
                transactionType = TransactionType.EXPENSES
            ),
            Transaction(
                amount = 300.0,
                date = LocalDate.now(),
                category = Category("Test 2"),
                transactionType = TransactionType.INCOME
            ),
            Transaction(
                amount = 150.0,
                date = LocalDate.now(),
                category = Category("Test 2"),
                transactionType = TransactionType.EXPENSES
            ),
        )
    }
}