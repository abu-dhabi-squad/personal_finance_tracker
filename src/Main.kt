import Models.Category
import Models.Transaction
import Models.TransactionType
import Models.UITransaction
import Services.TransactionService
import Services.TransactionValidator
import java.time.LocalDate

fun main() {
    val transactions = mutableListOf<Transaction>(Transaction(
        amount = 150.0,
        category = Category("Shopping"),
        transactionType =TransactionType.INCOME,
        date = LocalDate.of(2025, 4, 1)
    ))
    val tarnsV=TransactionService(transactions)
    // ترانزاكشن حقيقية

    println("قبل التعديل:")
    transactions.forEach { println(it) }

    // نحضر نسخة فيها تعديلات
    val updated = UITransaction(
        amount = 300.0, // تم تغييره
        category = Category("Shopping"), // نفس الكاتيجوري
        transactionType = TransactionType.INCOME, // نفس النوع
        date = "01-04-2025" // تم تغييره
    )

    val result = tarnsV.updateTransaction(transactions[0].id,  updated)

    println("\nتم التعديل؟ $result")
    println("\nبعد التعديل:")
    transactions.forEach { println(it) }
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
    if (TransactionValidator().isValidTransaction(transaction)) {
        if (trService.addTransaction(transaction)) {
            println("\nTransaction added successfully!")
            println(transaction)
        } else {
            println("\nTransaction can't be added")
        }
    } else {
        println("\nValidation failed, enter valid data")
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

}