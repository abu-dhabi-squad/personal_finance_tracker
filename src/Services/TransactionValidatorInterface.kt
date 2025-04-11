package Services

interface TransactionValidatorInterface {
    fun isValidAmount(amount: Double): Boolean
    fun isValidDate(date: String): Boolean
}