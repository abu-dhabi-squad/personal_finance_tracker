package Test

import Data.InFileTransactionRepository
import Data.InMemoryTransactionRepository
import Models.Category
import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import Services.TransactionService
import java.io.File
import java.io.ObjectOutputStream
import java.time.LocalDate
import java.util.*

// Add test functions for every feature and call it in main test file

class TransactionTests() {
    fun runAllTests() {
        println("Transaction Tests")
        testAddTransaction()
        testCanWithdraw()
        testEditTransaction()
        testDeleteTransactionFromMemory()
        testDeleteTransactionFromFile()
        testListAllTransactions()
    }

    private fun testAddTransaction() {
        val trService = TransactionService(balance = 0.0)
        val trSizeBefore = trService.getTransactionsSize()
        val validAddTransactionResult = trService.addTransaction(
            UITransaction(100.0, Category("Test 1"), TransactionType.INCOME, "03-03-2025")
        )
        val trSizeAfter = trService.getTransactionsSize()
        test(
            name = "check if transaction is added, using checking list size",
            actualResult = trSizeAfter,
            expectedResult = trSizeBefore + 1
        )
        test(
            name = "check if transaction is added, using return of add method",
            actualResult = validAddTransactionResult,
            expectedResult = true
        )
        test(
            name = "check if transaction is added, as it is EXPENSES and current balance is sufficient for the amount",
            actualResult = trService.addTransaction(
                UITransaction(50.0, Category("Test 1"), TransactionType.EXPENSES, "03-03-2025")
            ),
            expectedResult = true
        )
        test(
            name = "check if transaction is not added, as it is EXPENSES and current balance is not sufficient for the amount",
            actualResult = trService.addTransaction(
                UITransaction(200.0, Category("Test 1"), TransactionType.EXPENSES, "03-03-2025")
            ),
            expectedResult = false
        )
    }

    private fun testCanWithdraw() {
        val trService: TransactionService = TransactionService()
        var currentBalance = trService.getBalance()
        test(
            name = "check if balance is sufficient",
            actualResult = trService.canWithdraw(100.0),
            expectedResult = currentBalance >= 100.0
        )
        currentBalance = trService.getBalance()
        test(
            name = "check if balance is not sufficient",
            actualResult = trService.canWithdraw(currentBalance + 100),
            expectedResult = false
        )
    }

    private fun testEditTransaction() {
        val transactions = mutableListOf(
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
        val trService = TransactionService(
            transactions = transactions
        )
        val invalidEditedTransaction = UITransaction(
            amount = 150.0,
            date = "2025-04-04",
            category = Category("Test 2"),
            transactionType = TransactionType.EXPENSES
        )
        val editedTransaction = UITransaction(
            amount = 150.0,
            date = "2025-04-04",
            category = Category("Test 2"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "edit invalid transaction",
            actualResult = trService.updateTransaction(UUID.randomUUID(), invalidEditedTransaction),
            expectedResult = false
        )
        test(
            name = "edit transaction",
            actualResult = trService.updateTransaction(transactions[0].id, editedTransaction),
            expectedResult = true
        )
    }

    private fun testDeleteTransactionFromMemory() {
        val transactions = mutableListOf(
            Transaction(
                amount = 500.0,
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
        val trService = TransactionService(
            transactionRepo = InMemoryTransactionRepository(
                balance = 0.0,
                transactions = transactions
            )
        )
        test(
            name = "check if transaction is deleted from Memory, using return of add method",
            actualResult = trService.deleteTransaction(transactions[1]),
            expectedResult = true
        )
        test(
            name = "check if transaction is not deleted from Memory, as it is INCOME and current balance is not sufficient for returning money",
            actualResult = trService.deleteTransaction(transactions[1]),
            expectedResult = false
        )
    }

    private fun testDeleteTransactionFromFile() {
        val transactions = mutableListOf(
            Transaction(
                amount = 500.0,
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
        val trService = TransactionService(
            transactionRepo = InFileTransactionRepository(
                transactionsFile = File("out/transactionsTest.txt"),
                balanceFile = File("out/balanceTest.txt"),
            )
        )
        File("out/transactionsTest.txt").delete()
        test(
            name = "check if transaction is not deleted from File, as transactions file not found",
            actualResult = trService.deleteTransaction(transactions[1]),
            expectedResult = false
        )
        ObjectOutputStream(File("out/transactionsTest.txt").outputStream()).use {
            it.writeObject(mutableListOf<Transaction>())
        }
        test(
            name = "check if transaction is not deleted from File, as no transactions in the file",
            actualResult = trService.deleteTransaction(transactions[1]),
            expectedResult = false
        )
        val inFileTransactions = transactions.toMutableList()
        ObjectOutputStream(File("out/transactionsTest.txt").outputStream()).use {
            it.writeObject(transactions)
        }
        test(
            name = "check if transaction is deleted from File",
            actualResult = trService.deleteTransaction(inFileTransactions[1]),
            expectedResult = true
        )
        test(
            name = "check if transaction is not deleted from File, as it is INCOME and current balance is not sufficient for returning money",
            actualResult = trService.deleteTransaction(inFileTransactions[1]),
            expectedResult = false
        )
    }

    private fun testListAllTransactions() {
        val transactions = mutableListOf(
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
        val trService = TransactionService(
            transactions = transactions
        )
        test(
            name = "check if list is returned",
            actualResult = trService.listAllTransactions(),
            expectedResult = transactions
        )
    }
}