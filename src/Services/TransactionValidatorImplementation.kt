package Services

import Utils.DateParserInterface
import java.time.LocalDate

class TransactionValidatorImplementation(
    private val dateParser: DateParserInterface,
) : TransactionValidatorInterface {

    override
    fun isValidAmount(amount: Double): Boolean {
        return amount > 0
    }

    override
    fun isValidDate(date: String): Boolean {
        try {
            val localDate = dateParser.parseDateFromString(date)
            return !(dateParser.parseDateToString(localDate) != date || localDate > LocalDate.now())
        } catch (e: Exception) {
            return false
        }
    }
}