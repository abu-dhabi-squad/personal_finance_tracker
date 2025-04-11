package src.Data.Transaction

import Models.Transaction

interface TransactionInterface {
    fun add(transaction: Transaction): Boolean
    fun edit(transaction: Transaction): Boolean
    fun delete(transaction: Transaction): Boolean
    fun getAll(): List<Transaction>
    fun getByMonth(month: Int, year: Int): List<Transaction>
}
