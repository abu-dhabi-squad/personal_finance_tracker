package Data

import Models.Transaction
import Models.UITransaction
import java.util.*

class InMemoryTransactionRepository(val transactions: MutableList<Transaction>) : ITransactionRepository {
    override fun add(transaction: Transaction): Boolean {
        TODO("Not yet implemented")
    }

    override fun edit(id: UUID, transaction: UITransaction): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Transaction> {
        return transactions.toList()
    }

}
