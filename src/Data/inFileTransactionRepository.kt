package Data

import Models.Transaction
import Models.UITransaction
import java.io.File
import java.util.*

class inFileTransactionRepository(private val file: File):ITransactionRepository
//لما هناخد اسم الفايل من برا هسهل علينا التعديلات واقدر اغير في نوع واسم الفايل براحتي
{
    override fun add(transaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }


    override fun edit(id: UUID, transaction: UITransaction): Boolean {
        val transactions = TransactionFileHelper.readTransactions(file)
        val index = transactions.indexOfFirst { it.id == id }
        if (index == -1) return false
        TransactionFileHelper.writeTransactions(file, transactions)
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
