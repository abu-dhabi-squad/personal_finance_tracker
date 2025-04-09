package Data

import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

class InFileTransactionRepository(private val transactionsFile: File, private val balanceFile: File): ITransactionRepository {
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

    private fun writeBalance(balance: Double) {
        ObjectOutputStream(balanceFile.outputStream()).use {
            it.writeDouble(balance)
        }
    }

    private fun readBalance(): Double {
        if (!balanceFile.exists()) return 0.0
        return try {
            ObjectInputStream(balanceFile.inputStream()).use {
                it.readDouble()
            }
        } catch (e: Exception) {
            0.0
        }
    }

    override fun add(transaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }


    override fun edit(transaction: Transaction): Boolean {
//        val transactions = readTransactions()
//        val index = transactions.indexOfFirst { it.id == id }
//
//        if (index == -1) return false
//
//        val originalTransaction = transactions[index]
//        val editedTransaction = originalTransaction.copy(
//            amount = if (updatedTransaction.amount != originalTransaction.amount) updatedTransaction.amount else originalTransaction.amount,
//            category = if (updatedTransaction.category != originalTransaction.category) updatedTransaction.category else originalTransaction.category,
//            transactionType = if (updatedTransaction.transactionType != originalTransaction.transactionType) updatedTransaction.transactionType else originalTransaction.transactionType,
//            date = if (updatedTransaction.date != originalTransaction.date) updatedTransaction.date else originalTransaction.date
//        )
//
//        transactions[index] = editedTransaction
//        writeTransactions(transactions)
        return true
    }

    override fun delete(transaction: Transaction): Boolean {
        val transactions = readTransactions()
        if (transactions.contains(transaction)) {
            if (transaction.transactionType == TransactionType.INCOME && !canWithdraw(transaction.amount)) return false
            val success = transactions.remove(transaction)
            var balance = readBalance()
            if (success) {
                when(transaction.transactionType) {
                    TransactionType.INCOME -> balance -= transaction.amount
                    TransactionType.EXPENSES -> balance += transaction.amount
                }
                writeTransactions(transactions)
                writeBalance(balance)
                return true
            }
        }
        return false
    }

    override fun getAll(): List<Transaction> {
        return TransactionFileHelper.readTransactions(transactionsFile)
    }

    override fun getBalance(): Double {
        TODO("Not yet implemented")
    }

    override fun getByMonth(month: Int): List<Transaction> {
        TODO("Not yet implemented")
    }

    private fun canWithdraw(amount: Double): Boolean {
        return amount <= readBalance()
    }
}
