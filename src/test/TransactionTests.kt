package test

import Data.InFileTransactionImplementation
import Data.InMemoryTransactionImplementation
import Models.Category
import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import src.Services.Transaction.TransactionService
import src.Services.Transaction.TransactionValidatorImplementation
import Utils.DateParserImplementation
import src.test.test
import java.io.File
import java.io.ObjectOutputStream
import java.util.*

// Add test functions for every feature and call it in main test file

class TransactionTests() {
    fun runAllTests() {
        println("\n\n${"#".repeat(10)} Transaction Tests ${"#".repeat(10)}\n\n")
        testAddTransactionInMemory()
        testAddTransactionInFile()
        testEditTransactionInMemory()
        testEditTransactionInFile()
        testDeleteTransactionFromMemory()
        testDeleteTransactionFromFile()
        testListAllTransactionsFromMemory()
        testListAllTransactionsFromFile()
    }

    private fun testAddTransactionInMemory() {
        println("\n${"*".repeat(10)} Test Add Transaction In Memory ${"*".repeat(10)}\n")
        val trService = TransactionService(
            transactionData = InMemoryTransactionImplementation(),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )
        val validAddTransactionResult = trService.addTransaction(
            UITransaction(100.0, Category("Test 1"), TransactionType.INCOME, "03-03-2025")
        )
        test(
            name = "check if transaction is added to Memory, using return of add method",
            actualResult = validAddTransactionResult,
            expectedResult = true
        )
        test(
            name = "check if transaction is added to Memory, as it is EXPENSES and current balance is sufficient for the amount",
            actualResult = trService.addTransaction(
                UITransaction(50.0, Category("Test 1"), TransactionType.EXPENSES, "03-03-2025")
            ),
            expectedResult = true
        )
        test(
            name = "check if transaction is not added to Memory, as it is EXPENSES and current balance is not sufficient for the amount",
            actualResult = trService.addTransaction(
                UITransaction(200.0, Category("Test 1"), TransactionType.EXPENSES, "03-03-2025")
            ),
            expectedResult = false
        )
    }

    private fun testAddTransactionInFile() {
        println("\n${"*".repeat(10)} Test Add Transaction In File ${"*".repeat(10)}\n")
        File("out/transactionsTest.txt").delete()
        val trService = TransactionService(
            transactionData = InFileTransactionImplementation(File("out/transactionsTest.txt")),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )
        val validAddTransactionResult = trService.addTransaction(
            UITransaction(100.0, Category("Test 1"), TransactionType.INCOME, "03-03-2025")
        )
        test(
            name = "check if transaction is added to File, using return of add method",
            actualResult = validAddTransactionResult,
            expectedResult = true
        )
        test(
            name = "check if transaction is added to File, as it is EXPENSES and current balance is sufficient for the amount",
            actualResult = trService.addTransaction(
                UITransaction(50.0, Category("Test 1"), TransactionType.EXPENSES, "03-03-2025")
            ),
            expectedResult = true
        )
        test(
            name = "check if transaction is not added to File, as it is EXPENSES and current balance is not sufficient for the amount",
            actualResult = trService.addTransaction(
                UITransaction(200.0, Category("Test 1"), TransactionType.EXPENSES, "03-03-2025")
            ),
            expectedResult = false
        )
    }

    private fun testEditTransactionInMemory() {
        println("\n${"*".repeat(10)} Test Edit Transaction In Memory ${"*".repeat(10)}\n")
        val trService = TransactionService(
            transactionData = InMemoryTransactionImplementation(),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )
        listOf(
            UITransaction(
                amount = 100.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 200.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.EXPENSES
            ),
            UITransaction(
                amount = 300.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 150.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.EXPENSES
            ),
        ).forEach {
            trService.addTransaction(it)
        }
        val transactions = trService.listAllTransactions()
        val invalidEditedTransaction = UITransaction(
            amount = 150.0,
            date = "04-04-2025",
            category = Category("Test 2"),
            transactionType = TransactionType.EXPENSES
        )
        val editedTransaction = UITransaction(
            amount = 150.0,
            date = "04-04-2025",
            category = Category("Test 2"),
            transactionType = TransactionType.INCOME
        )
        test(
            name = "check if transaction is not updated on Memory",
            actualResult = trService.updateTransaction(UUID.randomUUID(), invalidEditedTransaction),
            expectedResult = false
        )
        test(
            name = "check if transaction is updated on Memory",
            actualResult = trService.updateTransaction(transactions[0].id, editedTransaction),
            expectedResult = true
        )
    }

    private fun testEditTransactionInFile() {
        println("\n${"*".repeat(10)} Test Edit Transaction In File ${"*".repeat(10)}\n")
        File("out/transactionsTest.txt").delete()
        val trService = TransactionService(
            transactionData = InFileTransactionImplementation(File("out/transactionsTest.txt")),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )
        listOf(
            UITransaction(
                amount = 100.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 200.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.EXPENSES
            ),
            UITransaction(
                amount = 300.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 150.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.EXPENSES
            ),
        ).forEach {
            trService.addTransaction(it)
        }
        val transactions = trService.listAllTransactions()
        val invalidEditedTransaction = UITransaction(
            amount = 150.0,
            date = "04-04-2025",
            category = Category("Test 2"),
            transactionType = TransactionType.EXPENSES
        )
        val editedTransaction = UITransaction(
            amount = 150.0,
            date = "04-04-2025",
            category = Category("Test 2"),
            transactionType = TransactionType.INCOME
        )
        test(
            name = "check if transaction is not updated on File",
            actualResult = trService.updateTransaction(UUID.randomUUID(), invalidEditedTransaction),
            expectedResult = false
        )
        test(
            name = "check if transaction is updated on File",
            actualResult = trService.updateTransaction(transactions[0].id, editedTransaction),
            expectedResult = true
        )
    }

    private fun testDeleteTransactionFromMemory() {
        println("\n${"*".repeat(10)} Test Delete Transaction From Memory ${"*".repeat(10)}\n")
        val trService = TransactionService(
            transactionData = InMemoryTransactionImplementation(),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )
        listOf(
            UITransaction(
                amount = 100.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 300.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 200.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.EXPENSES
            ),
            UITransaction(
                amount = 150.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.EXPENSES
            ),
        ).forEach {
            trService.addTransaction(it)
        }
        val transactions = trService.listAllTransactions()
        test(
            name = "check if transaction is deleted from Memory, using return of add method",
            actualResult = trService.deleteTransaction(transactions[2]),
            expectedResult = true
        )
        test(
            name = "check if transaction is not deleted from Memory, as it is INCOME and current balance is not sufficient for returning money",
            actualResult = trService.deleteTransaction(transactions[1]),
            expectedResult = false
        )
    }

    private fun testDeleteTransactionFromFile() {
        println("\n${"*".repeat(10)} Test Delete Transaction From File ${"*".repeat(10)}\n")
        File("out/transactionsTest.txt").delete()
        val trService = TransactionService(
            transactionData = InFileTransactionImplementation(File("out/transactionsTest.txt")),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )
        listOf(
            UITransaction(
                amount = 100.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 300.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 200.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.EXPENSES
            ),
            UITransaction(
                amount = 150.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.EXPENSES
            ),
        ).forEach {
            trService.addTransaction(it)
        }
        val transactions = trService.listAllTransactions()
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
            actualResult = trService.deleteTransaction(inFileTransactions[2]),
            expectedResult = true
        )
        test(
            name = "check if transaction is not deleted from File, as it is INCOME and current balance is not sufficient for returning money",
            actualResult = trService.deleteTransaction(inFileTransactions[1]),
            expectedResult = false
        )
    }

    private fun testListAllTransactionsFromMemory() {
        println("\n${"*".repeat(10)} Test List All Transaction From Memory ${"*".repeat(10)}\n")
        val trService = TransactionService(
            transactionData = InMemoryTransactionImplementation(),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )

        listOf(
            UITransaction(
                amount = 100.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 200.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.EXPENSES
            ),
            UITransaction(
                amount = 300.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 150.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.EXPENSES
            ),
        ).forEach {
            trService.addTransaction(it)
        }
        val transactions = trService.listAllTransactions()
        test(
            name = "check if list is returned",
            actualResult = trService.listAllTransactions(),
            expectedResult = transactions
        )
    }

    private fun testListAllTransactionsFromFile() {
        println("\n${"*".repeat(10)} Test List All Transaction From File ${"*".repeat(10)}\n")
        File("out/transactionsTest.txt").delete()
        val trService = TransactionService(
            transactionData = InFileTransactionImplementation(File("out/transactionsTest.txt")),
            validator = TransactionValidatorImplementation(DateParserImplementation()),
            dateParser = DateParserImplementation(),
        )
        listOf(
            UITransaction(
                amount = 100.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 200.0,
                date = "01-01-2025",
                category = Category("Test 1"),
                transactionType = TransactionType.EXPENSES
            ),
            UITransaction(
                amount = 300.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.INCOME
            ),
            UITransaction(
                amount = 150.0,
                date = "01-01-2025",
                category = Category("Test 2"),
                transactionType = TransactionType.EXPENSES
            ),
        ).forEach {
            trService.addTransaction(it)
        }
        val transactions = trService.listAllTransactions()
        test(
            name = "check if list is returned from File",
            actualResult = trService.listAllTransactions(),
            expectedResult = transactions
        )
    }
}