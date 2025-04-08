package Services

import Models.UITransaction

class TransactionValidator {
    fun isValidTransaction (transaction: UITransaction): Boolean {
        return false
    }

    private fun checkAmount(amount: Double): Boolean {
        return false
    }

    private fun checkDate(date: String): Boolean {
        return false
    }
}