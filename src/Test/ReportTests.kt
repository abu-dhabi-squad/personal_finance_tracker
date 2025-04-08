package Test

import Models.Category
import Models.Transaction
import Models.TransactionType
import Services.TransactionService
import java.time.LocalDate
import java.util.*

fun main(){
    var list:MutableList<Transaction> = mutableListOf(
        Transaction(100.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(10.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
        Transaction(120.0, Category("food"),TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
        Transaction(200.0, Category("medical"),TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
    )
    val transactionService: TransactionService = TransactionService(list)

    // balance test cases
    test("empty list",transactionService.getBalance(),0.0 )
    test("add Income with 1000.0 to list",transactionService.getBalance(),1000.0 )
    test("add Expenses with 1000.0 to list",transactionService.getBalance(),-1000.0 )

    //getByMonth test cases
    test("empty list",transactionService.getByMonth(1), mutableListOf<Transaction>())
    test("dont have the month in the list",transactionService.getByMonth(1), mutableListOf<Transaction>())
    test("invalid month",transactionService.getByMonth(13), mutableListOf<Transaction>())


}
// Add test functions for every feature and call it in main test file