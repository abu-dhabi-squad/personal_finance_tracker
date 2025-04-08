package Test

import Models.Category
import Models.Transaction
import Models.TransactionType
import Services.TransactionService
import java.time.LocalDate
import java.util.*

fun main(){

    var transactionService: TransactionService = TransactionService(mutableListOf<Transaction>())

    // balance test cases
    test("empty list",transactionService.getBalance(),0.0 )


    var list:MutableList<Transaction> = mutableListOf(
        Transaction(1000.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
         )
    transactionService = TransactionService(list)
    test("add Income with 1000.0 to list",transactionService.getBalance(),1000.0 )


    list = mutableListOf(
        Transaction(1000.0, Category("food"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
    )
    transactionService = TransactionService(list)
    test("add Expenses with 1000.0 to list",transactionService.getBalance(),-1000.0 )


    //getByMonth test cases
    transactionService = TransactionService(mutableListOf<Transaction>())
    test("empty list",transactionService.getByMonth(1), mutableListOf<Transaction>())


    list = mutableListOf(
        Transaction(100.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(200.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
    )
    transactionService = TransactionService(list)
    test("dont have the month in the list",transactionService.getByMonth(1), mutableListOf<Transaction>())


    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()
    list = mutableListOf(
        Transaction(100.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"),TransactionType.INCOME, LocalDate.of(2024,1,12), id1),
        Transaction(200.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(1000.0, Category("beauty"),TransactionType.EXPENSES, LocalDate.of(2024,1,13),id2),
    )
    transactionService = TransactionService(list)
    test(
        "have multiple months in list",
        transactionService.getByMonth(1),
        listOf(
            Transaction(120.0, Category("food"),TransactionType.INCOME, LocalDate.of(2024,1,12), id1),
            Transaction(1000.0, Category("beauty"),TransactionType.EXPENSES, LocalDate.of(2024,1,13), id2),)
    )



    test("invalid month",transactionService.getByMonth(13), mutableListOf<Transaction>())

}
// Add test functions for every feature and call it in main test file