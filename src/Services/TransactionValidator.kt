package Services

import Models.UITransaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionValidator {
    fun isValidTransaction (transaction: UITransaction): Boolean {
        return checkAmount(transaction.amount) && checkDate(transaction.date)
    }

    private fun checkAmount(amount: Double): Boolean {
        return amount > 0
    }

    private fun checkDate(date: String): Boolean {
        try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val localDate = LocalDate.parse(date, formatter)
            if (!formatter.format(localDate).equals(date) || localDate > LocalDate.now()) {
                return false
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }
}