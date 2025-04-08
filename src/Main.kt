import Models.Category
import Models.Transaction
import Models.TransactionType
import Services.ReportService
import java.time.LocalDate
import java.util.*

var list:MutableList<Transaction> = mutableListOf(
    Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
    Transaction(10.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()),
    Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()),
    Transaction(200.0, Category("medical"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID())
)
private val reportService: ReportService = ReportService(list)

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
    println(getMonthlySummaryFormat(month))
}

fun getMonthlySummaryFormat(month: Int?):String{
    val list = reportService.getSummaryByMonth(month)
    var result = "total transaction in month = "+ list.size+ "\n"
    result+= "amount | category | transactionType | date\n"
    list.forEach { trans->
        result+= trans.amount.toString() + " | "+
                trans.category.name + " | "+
                trans.transactionType + " | "+
                trans.date.toString() + "\n"
    }
    return result
}


fun getBalance(){
    println("the Balance = "+ reportService.getBalance())
}