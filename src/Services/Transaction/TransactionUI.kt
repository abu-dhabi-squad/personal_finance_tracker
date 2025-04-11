package src.Services.Transaction

import Models.TransactionType
import Models.UITransaction
import Utils.DateParserInterface
import src.Services.Category.CategoryService
import src.Services.Category.listAllCategories

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
    val category = when (catOption) {
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