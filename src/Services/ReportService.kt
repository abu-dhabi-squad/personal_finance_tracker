package Services

import Models.Transaction
import Models.TransactionType

class ReportService(private val transactions: MutableList<Transaction>) {

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

    fun getSummaryByMonth(month: Int?): List<Transaction> {
        if(month != null && month in 1 .. 12){
            return transactions.filter { transaction -> transaction.date.month.value == month }
        }
        return listOf()
    }

}
