package Services

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionValidatorImplementation: TransactionValidatorInterface {

    override
    fun isValidAmount(amount: Double): Boolean {
        return amount > 0
    }

    override
    fun isValidDate(date: String): Boolean {
        try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val localDate = LocalDate.parse(date, formatter)
            return !(!formatter.format(localDate).equals(date) || localDate > LocalDate.now())
        } catch (e: Exception) {
            return false
        }
    }
}