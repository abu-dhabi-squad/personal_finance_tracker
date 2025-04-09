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

    fun editTransaction(id: UUID, transaction: UITransaction): Boolean
    {
        fun editTransaction(id: UUID, uiTransaction: UITransaction): Boolean {
            val index = transactions.indexOfFirst { it.id == id }
//هنا انا ممكن استخدم mutlablemap هيخلي ال time complexity ==o(1) بدل ما هو هيكون o(n)
            if (index == -1) return false

            val existingTransaction = transactions[index]

            val updatedTransaction = chooseFieldToEdit(existingTransaction)

            transactions[index] = updatedTransaction
            return true
        }

        return false
    }
    fun getEditChoice(): Int? {
        println("Which field would you like to edit?")
        println("1. Amount")
        println("2. Category")
        println("3. Transaction Type")
        println("4. Date")
        println("5. All fields")
        print("Enter the number of the field to edit: ")

        return readLine()?.toIntOrNull()
    }

    fun chooseFieldToEdit(transaction: Transaction): Transaction {

        val choice = getEditChoice()
        return when (choice) {
            1 -> {
                print("Enter new amount: ")
                val newAmount = readLine()?.toDoubleOrNull()
                if (newAmount != null) transaction.copy(amount = newAmount) else transaction
            }
            2 -> {
                print("Enter new category name: ")
                val newCategory = readLine()?.trim()
                if (!newCategory.isNullOrEmpty()) transaction.copy(category = Category(newCategory)) else transaction
            }
            3 -> {
                println("Enter new transaction type (INCOME / EXPENSES): ")
                val newTypeInput = readLine()?.trim()?.uppercase()
                val newType = try {
                    TransactionType.valueOf(newTypeInput!!)
                } catch (e: Exception) {
                    null
                }
                if (newType != null) transaction.copy(transactionType = newType) else transaction
            }
            4 -> {
                print("Enter new date (yyyy-MM-dd): ")
                val newDateInput = readLine()
                val newDate = try {
                    LocalDate.parse(newDateInput)
                } catch (e: Exception) {
                    null
                }
                if (newDate != null) transaction.copy(date = newDate) else transaction
            }
            5 -> {
                print("Enter new amount: ")
                val newAmount = readLine()?.toDoubleOrNull() ?: transaction.amount

                print("Enter new category name: ")
                val newCategoryName = readLine()?.trim()
                val newCategory = if (!newCategoryName.isNullOrEmpty()) Category(newCategoryName) else transaction.category

                println("Enter new transaction type (INCOME / EXPENSES): ")
                val newTypeInput = readLine()?.trim()?.uppercase()
                val newTransactionType = try {
                    TransactionType.valueOf(newTypeInput!!)
                } catch (e: Exception) {
                    null
                } ?: transaction.transactionType

                print("Enter new date (yyyy-MM-dd): ")
                val newDateInput = readLine()
                val newDate = try {
                    LocalDate.parse(newDateInput)
                } catch (e: Exception) {
                    null
                } ?: transaction.date

                transaction.copy(
                    amount = newAmount,
                    category = newCategory,
                    transactionType = newTransactionType,
                    date = newDate
                )
            }
            else -> {
                println("Invalid choice. No changes made.")
                transaction
            }
        }
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
