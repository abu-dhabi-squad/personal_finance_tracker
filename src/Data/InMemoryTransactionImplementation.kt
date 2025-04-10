package Data

import Models.Transaction
import Models.TransactionType

class InMemoryTransactionImplementation: TransactionInterface {
    private val transactions: MutableList<Transaction> = mutableListOf()

    override fun add(transaction: Transaction): Boolean {
        if (transaction.transactionType == TransactionType.EXPENSES && !canWithdraw(transaction.amount)) return false
        return transactions.add(transaction)
    }

    override fun edit(transaction: Transaction): Boolean {
        val index = transactions.indexOfFirst { it.id == transaction.id }
        if (index == -1) return false
        transactions[index] = transaction
        return true
    }

    override fun delete(transaction: Transaction): Boolean {
        if (transactions.contains(transaction)) {
            if (transaction.transactionType == TransactionType.INCOME && !canWithdraw(transaction.amount)) return false
            val success = transactions.remove(transaction)
            if (success) return true
        }
        return false
    }

    override fun getAll(): List<Transaction> {
        return transactions
    }

    override fun getByMonth(month: Int, year: Int): List<Transaction> {
        return transactions.filter { transaction: Transaction -> transaction.date.month.value == month && transaction.date.year == year }.toList()
    }

    private fun getBalance(): Double {
        val sum = transactions.sumOf { transaction: Transaction ->
            if (transaction.transactionType == TransactionType.INCOME) transaction.amount else (transaction.amount * -1)
        }
        return sum
    }

    private fun canWithdraw(amount: Double): Boolean {
        return amount <= getBalance()
    }
}