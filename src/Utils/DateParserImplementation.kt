package Utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateParserImplementation: DateParserInterface {
    override fun parseDateFromString(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }

    override fun parseDateToString(date: LocalDate): String {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date)
    }
}