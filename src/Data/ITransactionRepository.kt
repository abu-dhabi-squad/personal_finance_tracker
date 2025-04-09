package Data

import Models.Transaction
import Models.UITransaction
import java.util.*

interface ITransactionRepository {

    fun add(transaction: Transaction): Boolean
    fun edit(transaction: Transaction): Boolean
    fun delete(transaction: Transaction): Boolean
    fun getAll(): List<Transaction>
    fun getBalance(): Double
    fun getByMonth(month: Int): List<Transaction>
}
