package test

import Models.Category
import Models.TransactionType
import Models.UITransaction
import src.Services.Transaction.TransactionValidatorImplementation
import Utils.DateParserImplementation

class TransactionValidatorTest {

    fun runAllTests() {
        println("\n\n${"#".repeat(10)} Transaction Validator Tests ${"#".repeat(10)}\n\n")
        testValidation()
    }

    private fun testValidation() {
        println("\n${"*".repeat(10)} Test Validations ${"*".repeat(10)}\n")
        val trValidator = TransactionValidatorImplementation(DateParserImplementation())
        var tr = UITransaction(
            amount = 150.0,
            date = "08-04-2025",
            category = Category("Cat 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Valid Transaction",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = true
        )

        tr = UITransaction(
            amount = -150.0,
            date = "08-04-2025",
            category = Category("Cat 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, negative amount",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )

        tr = UITransaction(
            amount = 0.0,
            date = "08-04-2025",
            category = Category("Cat 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, zero amount",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )

        tr = UITransaction(
            amount = 150.0,
            date = "08042025",
            category = Category("Cat 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, bad date format",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )

        tr = UITransaction(
            amount = 150.0,
            date = "29-02-2024",
            category = Category("Cat 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Valid Transaction, leap year (Feb 29)",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = true
        )

        tr = UITransaction(
            amount = 150.0,
            date = "29-02-1999",
            category = Category("Cat 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, non-leap year (Feb 29)",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )

        tr = UITransaction(
            amount = 150.0,
            date = "01-13-2025", // Invalid month
            category = Category("Invalid Month"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, bad month (13)",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )

        tr = UITransaction(
            amount = 150.0,
            date = "31-04-2025", // April has only 30 days
            category = Category("Invalid Day"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, bad day (April 31)",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )

        tr = UITransaction(
            amount = 150.0,
            date = "0C-0B-202A",
            category = Category("Bad Characters"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, characters in date",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )

        tr = UITransaction(
            amount = -150.0,
            date = "08042025",
            category = Category("Cat 1"),
            transactionType = TransactionType.EXPENSES
        )
        test(
            name = "Invalid Transaction, bad amount and date format",
            actualResult = trValidator.isValidAmount(tr.amount) && trValidator.isValidDate(tr.date),
            expectedResult = false
        )
    }
}