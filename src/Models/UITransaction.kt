package Models

data class UITransaction(
    val amount: Double,
    val category: Category,
    val transactionType: TransactionType,
    val date: String,
)