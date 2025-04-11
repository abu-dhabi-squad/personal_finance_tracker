package Services

import Models.Category
import Models.TransactionType

interface ReportValidatorInterface {
    fun isValidYear(year: Int):Boolean
    fun isValidMonth(month: Int):Boolean
    fun isValidcategory(category: Category):Boolean
    fun isValidTransactionType(transactionType: TransactionType): Boolean
}