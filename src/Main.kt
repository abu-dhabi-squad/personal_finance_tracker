import Models.Category
import Models.TransactionType
import Models.UITransaction
import Services.TransactionService

fun main() {

}

fun addTransaction(trService: TransactionService) {
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
    print("\nChoose transaction type: ")
    val trTypeInput: Int? = readln().toIntOrNull()
    if (trTypeInput != 1 && trTypeInput != 2) {
        println("\nInvalid input ...")
        return
    }
    val trType: TransactionType = if (trTypeInput == 1) TransactionType.INCOME else TransactionType.EXPENSES
    /// TODO: View all categories
    print("\nChoose category: ")
    val catInput: Int? = readln().toIntOrNull()
    if (catInput == null) {
        println("\nInvalid input ...")
        return
    }
    val category = Category("Test Category") /// TODO: Replace with actual category after merge

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

fun deleteTransaction(trService: TransactionService) {
    listAllTransactions(trService)
    val transactions = trService.listAllTransactions()
    print("\nSelect a transaction to delete (0 to cancel): ")
    var input: Int?
    do {
        input = readln().toIntOrNull()
        if (input == null || input < 0 || input > transactions.size) {
            println("\nEnter a valid option")
        } else {
            if (input == 0) break
            val success = trService.deleteTransaction(transactions[input - 1])
            if (success) {
                println("\nTransaction deleted successfully!\n")
            } else {
                println("\nTransaction can't be deleted\n")
            }
            break
        }
    } while (input != null)
}

fun listAllTransactions(trService: TransactionService) {
    val transactions = trService.listAllTransactions()
    if (transactions.isEmpty()) {
        println("No transactions found.")
    } else {
        println("All Transactions:")
        println("****************************************************")
        transactions.forEach { transaction ->
            println("ID: ${transaction.id}")
            println("Amount: ${transaction.amount}")
            println("Category: ${transaction.category.name}")
            println("Type: ${transaction.transactionType}")
            println("Date: ${transaction.date}")
            println("****************************************************")
        }
    }
}