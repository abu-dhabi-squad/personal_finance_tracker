import Services.TransactionService

fun main() {

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