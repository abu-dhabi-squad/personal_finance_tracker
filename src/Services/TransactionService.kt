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

    fun editTransaction(id: UUID, updatedTransaction: Transaction): Boolean {
        // find trans by id
        val index = transactions.indexOfFirst { it.id == id }

        if (index == -1) {
            // id not found
            return false
        }

        // assign orignal trans
        val originalTransaction = transactions[index]

        // update fields
        val transactionToUpdate = originalTransaction.copy(
            amount = if (updatedTransaction.amount != 0.0) updatedTransaction.amount else originalTransaction.amount,
            category = updatedTransaction.category.takeIf { it != originalTransaction.category } ?: originalTransaction.category,
            transactionType = updatedTransaction.transactionType.takeIf { it != originalTransaction.transactionType } ?: originalTransaction.transactionType,
            date = updatedTransaction.date.takeIf { it != originalTransaction.date } ?: originalTransaction.date
        )


        // save new trans
        transactions[index] = transactionToUpdate

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
