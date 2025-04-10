package Models

data class MonthTransactions(
    val month: Int,
    val year: Int,
    val totalIncome: Double,
    val totalExpenses: Double,
    val transactions: List<Transaction>
)
