package Data

import Models.Transaction
import Models.UITransaction
import java.util.*

interface ITransactionRepository {

    fun add(transaction: Transaction): Boolean
    fun edit(id: UUID, transaction: UITransaction): Boolean
    fun delete(id: UUID): Boolean
    fun getAll(): List<Transaction>
    fun getBalance(): Double
    fun getByMonth(month: Int): List<Transaction>
}
