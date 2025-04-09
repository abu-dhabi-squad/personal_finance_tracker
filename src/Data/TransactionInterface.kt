package Data

import Models.Transaction

interface TransactionInterface {
    fun add(transaction: Transaction): Boolean
    fun edit(transaction: Transaction): Boolean
    fun delete(transaction: Transaction): Boolean
    fun getAll(): List<Transaction>
}
