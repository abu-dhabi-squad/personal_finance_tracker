package Test

import Models.Category
import Models.TransactionType
import Models.UITransaction
import Services.TransactionValidator

class TransactionValidatorTest {

    fun runAllTests() {
        println("Transaction Validator Tests")
        testValidation()
    }

    private fun testValidation() {
        val trValidator = TransactionValidator()
        test(
            name = "Valid Transaction",
            actualResult = trValidator.isValidTransaction(UITransaction(
                amount = 150.0,
                date = "08-04-2025",
                category = Category("Cat 1"),
                transactionType = TransactionType.EXPENSES
            )),
            expectedResult = true
        )

        test(
            name = "Invalid Transaction, negative amount",
            actualResult = trValidator.isValidTransaction(UITransaction(
                amount = -150.0,
                date = "08-04-2025",
                category = Category("Cat 1"),
                transactionType = TransactionType.EXPENSES
            )),
            expectedResult = false
        )

        test(
            name = "Invalid Transaction, zero amount",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = 0.0,
                    date = "08-04-2025",
                    category = Category("Cat 1"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = false
        )

        test(
            name = "Invalid Transaction, bad date format",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = 150.0,
                    date = "08042025",
                    category = Category("Cat 1"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = false
        )

        test(
            name = "Valid Transaction, leap year (Feb 29)",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = 150.0,
                    date = "29-02-2024",
                    category = Category("Cat 1"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = true
        )

        test(
            name = "Invalid Transaction, non-leap year (Feb 29)",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = 150.0,
                    date = "29-02-1999",
                    category = Category("Cat 1"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = false
        )

        test(
            name = "Invalid Transaction, bad month (13)",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = 150.0,
                    date = "01-13-2025", // Invalid month
                    category = Category("Invalid Month"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = false
        )

        test(
            name = "Invalid Transaction, bad day (April 31)",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = 150.0,
                    date = "31-04-2025", // April has only 30 days
                    category = Category("Invalid Day"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = false
        )

        test(
            name = "Invalid Transaction, characters in date",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = 150.0,
                    date = "0C-0B-202A",
                    category = Category("Bad Characters"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = false
        )

        test(
            name = "Invalid Transaction, bad amount and date format",
            actualResult = trValidator.isValidTransaction(
                UITransaction(
                    amount = -150.0,
                    date = "08042025",
                    category = Category("Cat 1"),
                    transactionType = TransactionType.EXPENSES
                )
            ),
            expectedResult = false
        )
    }
}