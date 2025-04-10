package Services

interface ReportValidatorInterface {
    fun isValidYear(year: Int):Boolean
    fun isValidMonth(month: Int):Boolean
}