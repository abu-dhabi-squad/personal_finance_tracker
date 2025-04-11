import Data.InFileTransactionImplementation
import Utils.DateParserImplementation
import src.Data.InFileCategory
import src.Services.Category.CategoryService
import src.Services.Category.addCategory
import src.Services.Category.deleteCategory
import src.Services.Category.listAllCategories
import src.Services.Report.ReportService
import src.Services.Report.ReportValidatorImplementation
import src.Services.Report.getBalance
import src.Services.Report.getMonthlyReport
import src.Services.Transaction.*
import src.Utils.CategoryValidator
import java.io.File

fun main() {
    runUI()
}

fun showMenuOptions() {
    println("╔════════════════════════════════════╗")
    println("║          Personal Finance          ║")
    println("╠════════════════════════════════════╣")
    println("║ 1.    Add Category                 ║")
    println("║ 2.    Delete Category              ║")
    println("║ 3.    List All Categories          ║")
    println("║ 4.    Add Transaction              ║")
    println("║ 5.    Edit Transaction             ║")
    println("║ 6.    Delete Transaction           ║")
    println("║ 7.    List All Transactions        ║")
    println("║ 8.    Show Monthly Report          ║")
    println("║ 9.    Show Total Balance           ║")
    println("║ 0.    Exit                         ║")
    println("╚════════════════════════════════════╝")
}

fun runUI() {
    val dateParser = DateParserImplementation()
    val categoryImp = InFileCategory(File("out/Categories"))
    val transactionImp = InFileTransactionImplementation(File("out/Transactions"))
    val categoryService = CategoryService(
        categoryValidatorInterface = CategoryValidator(),
        categoryImp = categoryImp
    )
    val transactionService = TransactionService(
        transactionData = transactionImp,
        dateParser = dateParser,
        validator = TransactionValidatorImplementation(dateParser),
    )
    val reportService = ReportService(
        reportValidatorInterface = ReportValidatorImplementation(),
        transactionInterface = transactionImp
    )
    do {
        showMenuOptions()
        when (getUserChoiceNumber()) {
            1 -> {
                addCategory(categoryService)
            }

            2 -> {
                deleteCategory(categoryService)
            }

            3 -> {
                listAllCategories(categoryService)
            }

            4 -> {
                addTransaction(transactionService, categoryService)
            }

            5 -> {
                editTransaction(transactionService, categoryService, dateParser)
            }

            6 -> {
                deleteTransaction(transactionService)
            }

            7 -> {
                listAllTransactions(transactionService)
            }

            8 -> {
                getMonthlyReport(reportService)
            }

            9 -> {
                getBalance(reportService)
            }

            else -> return
        }
    } while (true)
}

fun getUserChoiceNumber(): Int? {
    print("\nEnter your choose: ")
    return readln().toIntOrNull()
}