package Data

import Models.Transaction
import Models.UITransaction
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

class inFileTransactionRepository(private val file: File):ITransactionRepository
//لما هناخد اسم الفايل من برا هسهل علينا التعديلات واقدر اغير في نوع واسم الفايل براحتي
{
    private fun readTransactions(): MutableList<Transaction> {
        if (!file.exists()) return mutableListOf()
        return try {
            ObjectInputStream(file.inputStream()).use {
                it.readObject() as MutableList<Transaction>
            }
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun writeTransactions(transactions: List<Transaction>) {
        ObjectOutputStream(file.outputStream()).use {
            it.writeObject(transactions)
        }
    }
    override fun add(transaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }


    override fun edit(id: UUID, updatedTransaction: Transaction): Boolean {
        val transactions = readTransactions()
        val index = transactions.indexOfFirst { it.id == id }

        if (index == -1) return false

        val originalTransaction = transactions[index]
        val editedTransaction = originalTransaction.copy(
            amount = if (updatedTransaction.amount != originalTransaction.amount) updatedTransaction.amount else originalTransaction.amount,
            category = if (updatedTransaction.category != originalTransaction.category) updatedTransaction.category else originalTransaction.category,
            transactionType = if (updatedTransaction.transactionType != originalTransaction.transactionType) updatedTransaction.transactionType else originalTransaction.transactionType,
            date = if (updatedTransaction.date != originalTransaction.date) updatedTransaction.date else originalTransaction.date
        )

        transactions[index] = editedTransaction
        writeTransactions(transactions)
        return true
    }

    override fun delete(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Transaction> {
        return TransactionFileHelper.readTransactions(file)
    }

    override fun getBalance(): Double {
        TODO("Not yet implemented")
    }

    override fun getByMonth(month: Int): List<Transaction> {
        TODO("Not yet implemented")
    }

}
