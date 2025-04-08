package Test

import Models.Category
import Models.TransactionType
import Models.UITransaction
import Services.TransactionValidator

class TransactionValidatorTest {
    fun testValidation() {
        val trValidator = TransactionValidator()
        test(
            name = "Valid Transaction",
            actualResult = trValidator.isValidTransaction(UITransaction(
                amount = 150.0,
                date = "2025-04-08",
                category = Category("Cat 1"),
                transactionType = TransactionType.EXPENSES
            )),
            expectedResult = true
        )
        test(
            name = "Invalid Transaction, bad amount",
            actualResult = UITransaction(
                amount = -150.0,
                date = "2025-04-08",
                category = Category("Cat 1"),
                transactionType = TransactionType.EXPENSES
            ),
            expectedResult = false
        )
        test(
            name = "Invalid Transaction, bad date format",
            actualResult = UITransaction(
                amount = 150.0,
                date = "20250408",
                category = Category("Cat 1"),
                transactionType = TransactionType.EXPENSES
            ),
            expectedResult = false
        )
        test(
            name = "Invalid Transaction, bad amount and date format",
            actualResult = UITransaction(
                amount = -150.0,
                date = "20250408",
                category = Category("Cat 1"),
                transactionType = TransactionType.EXPENSES
            ),
            expectedResult = false
        )
    }
}