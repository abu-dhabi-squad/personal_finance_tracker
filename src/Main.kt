import Data.InFileTransactionImplementation
import Data.InMemoryTransactionImplementation
import Models.Category
import Models.TransactionType
import Models.UITransaction
import Services.*
import Utils.DateParserImplementation
import Utils.DateParserInterface
import src.Data.InFileCategory
import src.Data.InMemoryCategory
import src.Utils.CategoryValidator
import java.io.File

fun main() {
    showMenu()
}

fun showMenu() {
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
        reportValidatorInterface = ReportValidatorImplementation(categoryImp),
        transactionInterface = transactionImp
    )
    do {
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

        when (getUserInput()) {
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

fun getUserInput(): Int? {
    print("\nEnter your choose: ")
    return readln().toIntOrNull()
}

fun addCategory(categoryService: CategoryService) {
    print("\nEnter category name: ")
    val catName = readln()
    val category = Category(catName)
    if (categoryService.addCategory(category)) {
        println("\nCategory is added successfully")
    } else {
        println("\nCategory is added successfully")
    }
}

fun deleteCategory(categoryService: CategoryService) {
    val categories = categoryService.getAllCategories()
    if (categories.isEmpty()) {
        println("\nNo categories found!")
        return
    }
    listAllCategories(categoryService)
    print("\nChoose category: ")
    val option = readln().toIntOrNull()
    if (option != null && option in 1..categories.size) {
        val category = categories[option - 1]
        if (categoryService.deleteCategory(category)) {
            println("\nCategory is deleted successfully")
        } else {
            println("\nCategory is deleted successfully")
        }
    } else {
        println("\nEnter valid choice!")
    }
}

fun addTransaction(trService: TransactionService, categoryService: CategoryService) {
    print("\nEnter amount: ")
    val amount: Double? = readln().toDoubleOrNull()
    if (amount == null) {
        println("\nInvalid input ...")
        return
    }
    print("\nEnter date as 'dd-MM-YYYY': ")
    val date: String = readln()
    println()
    println("1 - Income:")
    println("2 - Expenses:")
    print("Choose transaction type: ")
    val trTypeInput: Int? = readln().toIntOrNull()
    if (trTypeInput != 1 && trTypeInput != 2) {
        println("\nInvalid input ...")
        return
    }
    val trType: TransactionType = if (trTypeInput == 1) TransactionType.INCOME else TransactionType.EXPENSES

    listAllCategories(categoryService)
    val categories = categoryService.getAllCategories()
    print("\nChoose category: ")
    val catOption = readln().toIntOrNull()
    if (catOption == null || catOption !in 1..categories.size) {
        println("\nInvalid input ...")
        return
    }
    val category = categories[catOption - 1]

    val transaction = UITransaction(
        amount = amount,
        transactionType = trType,
        date = date,
        category = category
    )

    if (trService.addTransaction(transaction)) {
        println("\nTransaction added successfully!")
        println(transaction)
    } else {
        println("\nTransaction can't be added")
    }
}

fun editTransaction(trService: TransactionService, categoryService: CategoryService, dateParser: DateParserInterface) {
    listAllTransactions(trService)
    val transactions = trService.listAllTransactions()
    print("\nSelect a transaction to edit (0 to cancel): ")
    val input = readln().toIntOrNull()
    if (input == null || input !in 0..transactions.size) {
        println("\nEnter a valid option!")
        return
    } else if (input == 0) return
    val originalTransaction = transactions[input - 1]
    print("\nEnter amount (leave blank to skip): ")
    var amount: Double? = readln().toDoubleOrNull()
    if (amount == null) {
        amount = originalTransaction.amount
    }
    print("\nEnter date as 'dd-MM-YYYY' (leave blank to skip): ")
    var date: String = readln()
    if (date.isBlank()) {
        date = dateParser.parseDateToString(originalTransaction.date)
    }
    println()
    println("1 - Income:")
    println("2 - Expenses:")
    print("Choose transaction type (leave blank to skip): ")
    val trTypeInput: Int? = readln().toIntOrNull()
    if (trTypeInput != null && trTypeInput != 1 && trTypeInput != 2) {
        println("\nInvalid input ...")
        return
    }
    val trType: TransactionType =
        when (trTypeInput) {
            null -> originalTransaction.transactionType
            1 -> TransactionType.INCOME
            else -> TransactionType.EXPENSES
        }

    listAllCategories(categoryService)
    val categories = categoryService.getAllCategories()
    print("\nChoose category (leave blank to skip): ")
    val catOption = readln().toIntOrNull()
    if (catOption != null && catOption !in 1..categories.size) {
        println("\nInvalid input ...")
        return
    }
    val category = when(catOption) {
        null -> originalTransaction.category
        else -> categories[catOption - 1]
    }

    val transaction = UITransaction(
        amount = amount,
        transactionType = trType,
        date = date,
        category = category
    )

    if (trService.addTransaction(transaction)) {
        println("\nTransaction updated successfully!")
        println(transaction)
    } else {
        println("\nTransaction can't be updated")
    }
}

fun deleteTransaction(trService: TransactionService) {
    listAllTransactions(trService)
    val transactions = trService.listAllTransactions()
    print("\nSelect a transaction to delete (0 to cancel): ")
    val input = readln().toIntOrNull()
    if (input == null || input < 0 || input > transactions.size) {
        println("\nEnter a valid option")
    } else {
        if (input == 0) return
        val success = trService.deleteTransaction(transactions[input - 1])
        if (success) {
            println("\nTransaction deleted successfully!\n")
        } else {
            println("\nTransaction can't be deleted\n")
        }
    }
}

fun listAllTransactions(trService: TransactionService) {
    val transactions = trService.listAllTransactions()
    if (transactions.isEmpty()) {
        println("No transactions found.")
    } else {
        println("All Transactions:")
        transactions.forEachIndexed { i, transaction ->
            println("Transaction ${i + 1}:")
            println("\tID: ${transaction.id}")
            println("\tAmount: ${transaction.amount}")
            println("\tCategory: ${transaction.category.name}")
            println("\tType: ${transaction.transactionType}")
            println("\tDate: ${transaction.date}")
            println("\n")
        }
    }
}

fun listAllCategories(categoriesService: CategoryService) {
    val allCategories = categoriesService.getAllCategories()
    if (allCategories.isNotEmpty()) {
        allCategories.forEachIndexed { index, category ->
            println("${index + 1}) - ${category.name}")
        }
    } else {
        println("\nNo Categories found!")
    }
}

fun getMonthlyReport(reportService: ReportService) {
    print("\nEnter month [1-12]: ")
    val month = readln().toIntOrNull()
    if (month == null) {
        println("\ninvalid month!")
        return
    }
    print("\nEnter year: ")
    val year = readln().toIntOrNull()
    if (year == null) {
        println("\ninvalid year")
        return
    }

    println(getMonthlySummaryFormat(reportService, month, year))
}

private fun getMonthlySummaryFormat(reportService: ReportService, month: Int, year: Int): String {
    val monthTransactions = reportService.getSummaryByMonth(month, year)
    if (monthTransactions.transactions.isEmpty()) {
        return "\nNo transaction in this date"
    }
    var result = "\nmonth = " + month + "\nyear = " + year + "\n"
    result += "total transaction in month = " + monthTransactions.transactions.size + "\n"
    result += "total income in month = " + monthTransactions.totalIncome + "\n"
    result += "total expenses in month = " + monthTransactions.totalExpenses + "\n\n"
    result += String.format("%-18s | %-18s  | %-18s  | %-18s  |\n", "amount", "category", "transaction Type", "date")
    monthTransactions.transactions.forEach { trans ->
        result += String.format("%-18s |", trans.amount.toString()) + "  " +
                String.format("%-18s |", trans.category.name) + "  " +
                String.format("%-18s |", trans.transactionType.name) + "  " +
                String.format("%-18s |", trans.date.toString()) + "\n"
    }
    return result
}

fun getBalance(reportService: ReportService) {
    println("the Balance = " + reportService.getBalance())
}