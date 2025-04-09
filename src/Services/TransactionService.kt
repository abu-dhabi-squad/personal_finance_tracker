package Services

import Data.ITransactionRepository
import Data.InMemoryTransactionRepository
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
    private val transactionRepo: ITransactionRepository = InMemoryTransactionRepository(transactions = transactions, balance = balance),
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

        fun updateTransaction(id: UUID, uiTransaction: UITransaction): Boolean {
            val validator = TransactionValidator()
             val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            // 1. التحقق من صحة البيانات
            if (!validator.isValidTransaction(uiTransaction)) {
                return false
            }
            // 2. البحث عن المعاملة الحالية
            val existingIndex = transactions.indexOfFirst { it.id == id }
            if (existingIndex == -1) throw NoSuchElementException("Transaction not found")
            val existing = transactions[existingIndex]

            // التحديث الجزئي
            val updatedTransaction = existing.copy(
                amount = uiTransaction.amount ?: existing.amount,
                category = uiTransaction.category ?: existing.category,
                transactionType =uiTransaction.transactionType ?: existing.transactionType,
                date = uiTransaction.date?.let { LocalDate.parse(it, dateFormatter) } ?: existing.date
            )
//
            transactions[existingIndex] = updatedTransaction
            return true
        }


    fun deleteTransaction(transaction: Transaction): Boolean {
        return transactionRepo.delete(transaction)
    }

    fun listAllTransactions(): List<Transaction> {
        if (transactions.isEmpty()) {
            println("No transactions found.")
        } else {
            println("All Transactions:")
            println("****************************************************")
            transactions.forEach { transaction ->
                println("ID: ${transaction.id}")
                println("Amount: ${transaction.amount}")
                println("Category: ${transaction.category.name}")
                println("Type: ${transaction.transactionType}")
                println("Date: ${transaction.date}")
                println("****************************************************")
            }
        }
        return transactions
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
