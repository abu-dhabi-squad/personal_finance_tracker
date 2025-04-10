package Utils

import java.time.LocalDate

interface DateParserInterface {
    fun parseDateFromString(date: String): LocalDate
    fun parseDateToString(date: LocalDate): String
}