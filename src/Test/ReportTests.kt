package Test


import Data.InMemoryTransactionImplementation
import Models.Category
import Models.MonthTransactions
import Models.Transaction
import Models.TransactionType
import Services.ReportService
import java.time.LocalDate
import java.util.*

fun main() {
    // balance test cases

    var reportService = ReportService(InMemoryTransactionImplementation())
    test("empty list", reportService.getBalance(), 0.0)

    /* var list: MutableList<Transaction> = mutableListOf(
         Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
     )*/
    var memory = InMemoryTransactionImplementation()
    memory.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
    reportService = ReportService(memory)
    test("add Income with 1000.0 to list", reportService.getBalance(), 1000.0)

    /*list = mutableListOf(
        Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
    )*/
    memory = InMemoryTransactionImplementation()
    memory.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
    memory.add(Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()) )
    reportService = ReportService(memory)

    test("add Expenses with 1000.0 to list", reportService.getBalance(), 0.0)

    /*list = mutableListOf(
        Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(1800.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
    )*/

    memory = InMemoryTransactionImplementation()
    memory.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
    memory.add(Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()) )
    memory.add(Transaction(1800.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()) )
    reportService = ReportService(memory)

    test("add multiple transactions", reportService.getBalance(), 1800.0)

    //getByMonth test cases
    reportService = ReportService(InMemoryTransactionImplementation())
    test("empty list", reportService.getSummaryByMonth(1, 2025), MonthTransactions(1, 2025, 0.0, 0.0, listOf()))

    /*list = mutableListOf(
        Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(200.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
    )*/
    memory = InMemoryTransactionImplementation()
    memory.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
    memory.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()) )
    memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()) )
    memory.add(Transaction(200.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
    reportService = ReportService(memory)

    test(
        "dont have the month in the list",
        reportService.getSummaryByMonth(1, 2025),
        MonthTransactions(1, 2025, 0.0, 0.0, listOf())
    )

    /*list = mutableListOf(
        Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(200.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
    )*/
    memory = InMemoryTransactionImplementation()
    memory.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
    memory.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()) )
    memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()) )
    reportService = ReportService(memory)

    test(
        "dont have the year in the list",
        reportService.getSummaryByMonth(1, 0),
        MonthTransactions(1, 0, 0.0, 0.0, listOf())
    )

    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    /*list = mutableListOf(
        Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
        Transaction(200.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(1000.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2),
    )*/
    memory = InMemoryTransactionImplementation()
    memory.add(Transaction(100.0, Category("food"), TransactionType.INCOME,LocalDate.of(2024, 1, 12), id1))
    memory.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()) )
    memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()) )
    memory.add(Transaction(10.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2))
    reportService = ReportService(memory)

    test(
        "have multiple months in list",
        reportService.getSummaryByMonth(1, 2024),
        MonthTransactions(
            1, 2024, 100.0, 10.0, listOf(
                Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
                Transaction(10.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2),
            )
        )

    )

    test("invalid month", reportService.getSummaryByMonth(13, 2025), MonthTransactions(13, 2025, 0.0, 0.0, listOf()))

}