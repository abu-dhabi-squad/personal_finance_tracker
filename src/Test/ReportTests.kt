package Test

import Models.Category
import Models.Transaction
import Models.TransactionType
import Services.ReportService
import java.time.LocalDate
import java.util.*

fun main(){

    var reportService = ReportService(mutableListOf<Transaction>())

    // balance test cases
    test("empty list",reportService.getBalance(),0.0 )


    var list:MutableList<Transaction> = mutableListOf(
        Transaction(1000.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
         )
    reportService = ReportService(list)
    test("add Income with 1000.0 to list",reportService.getBalance(),1000.0 )


    list = mutableListOf(
        Transaction(1000.0, Category("food"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
    )
    reportService = ReportService(list)
    test("add Expenses with 1000.0 to list",reportService.getBalance(),-1000.0 )


    //getByMonth test cases
    reportService = ReportService(mutableListOf<Transaction>())
    test("empty list",reportService.getSummaryByMonth(1), mutableListOf<Transaction>())


    list = mutableListOf(
        Transaction(100.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(200.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
    )
    reportService = ReportService(list)
    test("dont have the month in the list",reportService.getSummaryByMonth(1), mutableListOf<Transaction>())


    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    list = mutableListOf(
        Transaction(100.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"),TransactionType.INCOME, LocalDate.of(2024,1,12), id1),
        Transaction(200.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(1000.0, Category("beauty"),TransactionType.EXPENSES, LocalDate.of(2024,1,13),id2),
    )
    reportService = ReportService(list)
    test(
        "have multiple months in list",
        reportService.getSummaryByMonth(1),
        listOf(
            Transaction(120.0, Category("food"),TransactionType.INCOME, LocalDate.of(2024,1,12), id1),
            Transaction(1000.0, Category("beauty"),TransactionType.EXPENSES, LocalDate.of(2024,1,13), id2),)
    )



    test("invalid month",reportService.getSummaryByMonth(13), mutableListOf<Transaction>())

}
// Add test functions for every feature and call it in main test file