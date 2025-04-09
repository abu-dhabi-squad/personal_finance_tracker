package Data

import Models.Transaction
import Models.UITransaction
import java.util.*

class InMemoryTransactionRepository : ITransactionRepository {
    //private val transactions = mutableListOf<Transaction>()
    override fun add(transaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }

    override fun edit(id: UUID, updatedTransaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Transaction> {
        TODO("Not yet implemented")
    }

    override fun getBalance(): Double {
        TODO("Not yet implemented")
    }

    override fun getByMonth(month: Int): List<Transaction> {
        TODO("Not yet implemented")
    }
}
