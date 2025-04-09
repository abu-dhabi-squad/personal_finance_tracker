package Data

import Models.Transaction
import Models.TransactionType

class InMemoryTransactionImplementation(
    private val transactions: MutableList<Transaction> = mutableListOf(),
    private var balance: Double = 0.0,
) : TransactionInterface {

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
            if (success) {
                when(transaction.transactionType) {
                    TransactionType.INCOME -> balance -= transaction.amount
                    TransactionType.EXPENSES -> balance += transaction.amount
                }
                return true
            }
        }
        return false
    }

    override fun getAll(): List<Transaction> {
        return transactions
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
