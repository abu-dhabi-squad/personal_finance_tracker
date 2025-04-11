package src.Services.Report

import java.time.LocalDate

class ReportValidatorImplementation: ReportValidatorInterface {
    override fun isValidYear(year: Int): Boolean = ( year in 1900..LocalDate.now().year)

    override fun isValidMonth(month: Int) :Boolean =  (month in 1..12)
}