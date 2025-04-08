package Services

import Models.Transaction

class TransactionService(
    private val transactions: MutableList<Transaction> = mutableListOf()
) {

}