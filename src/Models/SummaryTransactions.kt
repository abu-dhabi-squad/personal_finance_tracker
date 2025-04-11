package Models

data class SummaryTransactions(
    val totalIncome: Double,
    val totalExpenses: Double,
    val transactions: List<Transaction>
)
