package Services

import Models.Category
import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TransactionService(
    private val transactions: MutableList<Transaction> = mutableListOf(),
    private var balance: Double = 0.0,
) {

    fun getTransactionsSize(): Int = transactions.size

    fun addTransaction(transaction: UITransaction): Boolean {
        if (transaction.transactionType == TransactionType.EXPENSES && !canWithdraw(transaction.amount)) return false
        transactions.add(Transaction(
            amount = transaction.amount,
            category = transaction.category,
            transactionType = transaction.transactionType,
            date = LocalDate.parse(transaction.date, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
        ))
        when(transaction.transactionType) {
            TransactionType.INCOME -> balance += transaction.amount
            TransactionType.EXPENSES -> balance -= transaction.amount
        }
        return true
    }

    fun canWithdraw(amount: Double): Boolean {
        return amount <= balance
    }

    fun editTransaction(id: UUID, transaction: UITransaction): Boolean {
        return false
    }

    fun isTransactionExists(transaction: Transaction): Boolean {
        return transactions.contains(transaction)
    }

    fun deleteTransaction(transaction: Transaction): Boolean {
        if (isTransactionExists(transaction)) {
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