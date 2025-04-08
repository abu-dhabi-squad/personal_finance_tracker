package Test

import Models.Category
import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import Services.TransactionService
import java.time.LocalDate
import java.util.UUID

// Add test functions for every feature and call it in main test file

class TransactionTests() {
    fun testAddTransaction() {
        val trService: TransactionService = TransactionService()
        val trSizeBefore = trService.getTransactionsSize()
        val addTransactionResult = trService.addTransaction(
            UITransaction(
                amount = 100.0,
                date = "2025-03-03",
                category = Category("Test 1"),
                transactionType = TransactionType.INCOME
            )
        )
        val trSizeAfter = trService.getTransactionsSize()
        test(
            name = "check if transaction is added, using checking list size",
            actualResult = trSizeAfter,
            expectedResult = trSizeBefore + 1
        )
        test(
            name = "check if transaction is added, using return of add method",
            actualResult = addTransactionResult,
            expectedResult = true
        )
    }

    fun testCanWithdraw() {
        val trService: TransactionService = TransactionService()
        var currentBalance = trService.getBalance()
        test(
            name = "check if balance is sufficient",
            actualResult = trService.canWithdraw(100.0),
            expectedResult = currentBalance >= 100.0
        )
        currentBalance = trService.getBalance()
        test(
            name = "check if balance is sufficient",
            actualResult = trService.canWithdraw(currentBalance + 100),
            expectedResult = false
        )
    }

    fun testEditTransaction() {
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
            actualResult = trService.editTransaction(UUID.randomUUID(), invalidEditedTransaction),
            expectedResult = false
        )
        test(
            name = "edit transaction",
            actualResult = trService.editTransaction(transactions[0].id, editedTransaction),
            expectedResult = true
        )
    }

    fun testIsTransactionExists() {
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
            name = "check if id exists",
            actualResult = trService.isTransactionExists(UUID.randomUUID()),
            expectedResult = -1
        )
        test(
            name = "check if id exists",
            actualResult = trService.isTransactionExists(transactions[1].id),
            expectedResult = 1
        )
    }

    fun testDeleteTransaction() {
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
        val trSizeBefore = trService.getTransactionsSize()
        val deleteTransactionResult = trService.deleteTransaction(transactions[1].id)
        val trSizeAfter = trService.getTransactionsSize()
        test(
            name = "check if transaction is deleted, using checking list size",
            actualResult = trSizeAfter,
            expectedResult = trSizeBefore - 1
        )
        test(
            name = "check if transaction is deleted, using return of add method",
            actualResult = deleteTransactionResult,
            expectedResult = true
        )
    }

    fun testListAllTransactions() {
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