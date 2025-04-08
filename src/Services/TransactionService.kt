package Services

import Models.Category
import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import Test.test
import java.time.LocalDate
import java.util.*

class TransactionService(
    private val transactions: MutableList<Transaction> = mutableListOf()
) {
    private val balance: Double = 1000.0

    fun getTransactionsSize(): Int = transactions.size

    fun addTransaction(transaction: UITransaction): Boolean {
        return false
    }

    fun canWithdraw(amount: Double): Boolean {
        return false
    }

    fun editTransaction(id: UUID, transaction: UITransaction): Boolean {
        return false
    }

    fun isTransactionExists(transaction: Transaction): Int {
        return transactions.indexOf(transaction)
    }

    fun deleteTransaction(transaction: Transaction): Boolean {
        val index = isTransactionExists(transaction)
        if (index != -1) {
            return transactions.remove(transaction)
        }
        return false
    }

    fun listAllTransactions(): List<Transaction> {
        return listOf()
    }

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