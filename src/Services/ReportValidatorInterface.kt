package Services

import Models.Category

interface ReportValidatorInterface {
    fun isValidYear(year: Int):Boolean
    fun isValidMonth(month: Int):Boolean
    fun isValidcategory(category: Category):Boolean
}