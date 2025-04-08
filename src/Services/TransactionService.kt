package Services

import Models.Category
import Models.Transaction
import Models.TransactionType
import java.time.LocalDate

class TransactionService(
    private val transactions: MutableList<Transaction> = mutableListOf()
) {


    fun getBalance(): Double {
        var balance: Double = 0.0
        transactions.forEach { trans ->
            when (trans.transactionType.name) {
                TransactionType.INCOME.toString() -> balance += trans.amount
                TransactionType.EXPENSES.toString() -> balance -= trans.amount
            }

        }
        return balance
    }

    fun getByMonth(month: Int): List<Transaction> {

        return listOf<Transaction>()
    }
}