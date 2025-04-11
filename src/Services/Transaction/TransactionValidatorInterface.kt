package src.Services.Transaction

interface TransactionValidatorInterface {
    fun isValidAmount(amount: Double): Boolean
    fun isValidDate(date: String): Boolean
}