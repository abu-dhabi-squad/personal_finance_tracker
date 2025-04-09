package UI

import Data.InMemoryTransactionRepository
import Models.Category
import Models.Transaction
import Models.TransactionType
import Services.ReportService
import java.time.LocalDate
import java.util.*

var list: MutableList<Transaction> = mutableListOf(
    Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
    Transaction(10.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
    Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
    Transaction(200.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
)
private val reportService: ReportService = ReportService(InMemoryTransactionRepository(list))

fun main() {
    showMenu()
}

fun showMenu() {
    while (true) {
        println(
            "\nThe menu \n" +
                    "1 - get Monthly Report \n" +
                    "2 - get Balance \n" +
                    "3 - exit \n\n"
        )
        when (getUserInput()) {
            1 -> getMonthlyReport(reportService)
            2 -> getBalance(reportService)
            3 -> return
            else -> println("vaild choose")
        }
    }
}

fun getUserInput(): Int? {
    print("Enter your choose: ")
    return readln().toIntOrNull()
}