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
    fun runAllTests() {
        println("Transaction Tests")
        testAddTransaction()
        testCanWithdraw()
        testEditTransaction()
        testIsTransactionExists()
        testDeleteTransaction()
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

    private fun testIsTransactionExists() {
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
        val invalidTransaction = Transaction(
            amount = 150.0,
            date = LocalDate.now(),
            category = Category("Test 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "check if transaction not exists",
            actualResult = trService.isTransactionExists(invalidTransaction),
            expectedResult = false
        )
        test(
            name = "check if transaction exists",
            actualResult = trService.isTransactionExists(transactions[1]),
            expectedResult = true
        )
    }

    private fun testDeleteTransaction() {
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
            balance = 0.0,
            transactions = transactions
        )
        val trSizeBefore = trService.getTransactionsSize()
        val deleteTransactionResult = trService.deleteTransaction(transactions[1])
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
        test(
            name = "check if transaction is not deleted, as it is INCOME and current balance is not sufficient for returning money",
            actualResult = trService.deleteTransaction(transactions[1]),
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