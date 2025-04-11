package Services

import Models.Category
import Models.TransactionType
import src.Data.CategoryInterface
import java.time.LocalDate

class ReportValidatorImplementation(val categoryInterface: CategoryInterface): ReportValidatorInterface {
    override fun isValidYear(year: Int): Boolean = ( year in 1900..LocalDate.now().year)

    override fun isValidMonth(month: Int) :Boolean =  (month in 1..12)

    override fun isValidcategory(category: Category): Boolean {
       val categories = categoryInterface.getAll()
        val isExsit = categories.any {
            it.name.trim().equals(category.name.trim(), true)
        }
        return isExsit
    }

    override fun isValidTransactionType(transactionType: TransactionType): Boolean =
        transactionType in TransactionType.entries.toTypedArray()
}