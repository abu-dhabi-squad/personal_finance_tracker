package Data

import Models.Transaction
import Models.TransactionType
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class InFileTransactionImplementation(private val transactionsFile: File) : TransactionInterface {
    private fun readTransactions(): MutableList<Transaction> {
        if (!transactionsFile.exists()) return mutableListOf()
        return try {
            ObjectInputStream(transactionsFile.inputStream()).use {
                it.readObject() as MutableList<Transaction>
            }
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun writeTransactions(transactions: List<Transaction>) {
        ObjectOutputStream(transactionsFile.outputStream()).use {
            it.writeObject(transactions)
        }
    }

    override fun add(transaction: Transaction): Boolean {
        if (transaction.transactionType == TransactionType.EXPENSES && !canWithdraw(transaction.amount)) return false
        val transactions = readTransactions()
        val result = transactions.add(transaction)
        writeTransactions(transactions)
        return result
    }

    override fun edit(transaction: Transaction): Boolean {
        val transactions = readTransactions()
        val index = transactions.indexOfFirst { it.id == transaction.id }
        if (index == -1) return false
        transactions[index] = transaction
        writeTransactions(transactions)
        return true
    }

    override fun delete(transaction: Transaction): Boolean {
        val transactions = readTransactions()
        if (transactions.contains(transaction)) {
            if (transaction.transactionType == TransactionType.INCOME && !canWithdraw(transaction.amount)) return false
            val success = transactions.remove(transaction)
            if (success) {
                writeTransactions(transactions)
                return true
            }
        }
        return false
    }

    override fun getAll(): List<Transaction> {
        return FileHelper.readList(transactionsFile)
    }

    override fun getByMonth(month: Int, year: Int): List<Transaction> {
        val transactions = readTransactions()
        return transactions.filter { transaction: Transaction -> transaction.date.month.value == month && transaction.date.year == year }.toList()
    }

    private fun getBalance(): Double {
        val sum = readTransactions().sumOf { transaction: Transaction ->
            if (transaction.transactionType == TransactionType.INCOME) transaction.amount else transaction.amount * -1
        }
        return sum
    }

    private fun canWithdraw(amount: Double): Boolean {
        return amount <= getBalance()
    }
}
