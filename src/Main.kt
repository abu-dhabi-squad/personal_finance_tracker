import Models.Category
import Models.Transaction
import Models.TransactionType
import Services.ReportService
import Services.TransactionService
import java.time.LocalDate
import java.util.*

var list:MutableList<Transaction> = mutableListOf(
    Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
    Transaction(10.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
    Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
    Transaction(200.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
)
private val transactionService:TransactionService = TransactionService(list)
private val reportService: ReportService = ReportService(transactionService)

fun main() {
    showMenu()
}

fun showMenu(){
    while (true){
        println("\nThe menu \n"+
                "1 - get Monthly Report \n"+
                "2 - get Balance \n"+
                "3 - exit \n\n"
        )

        when(getUserInput()){
            1 -> getMonthlyReport()
            2 -> getBalance()
            3 -> return
            else -> println("vaild choose")
        }
    }
}

fun getUserInput():Int?{

    print("Enter your choose: ")
    return readln().toIntOrNull()
}

fun getMonthlyReport(){
    print("please enter the month [1-12]: ")
    val month = readln().toIntOrNull()
    println(reportService.getMonthlySummary(month))
}

fun getBalance(){
    println(reportService.getBalance())
}