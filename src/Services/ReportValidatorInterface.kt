package Services

import Models.Category
import Models.TransactionType

interface ReportValidatorInterface {
    fun isValidYear(year: Int):Boolean
    fun isValidMonth(month: Int):Boolean
}