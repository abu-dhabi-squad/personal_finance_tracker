package Services

class ReportService(private val transactionService: TransactionService) {

    fun getBalance(): String{
        return "the Balance = "+ transactionService.getBalance()
    }

    fun getMonthlySummary(month: Int?):String{
        if(month != null && month in 1 .. 12){
            val list = transactionService.getByMonth(month)
            var result = "total transaction in month = "+ list.size+ "\n"
            result+= "amount | category | transactionType | date\n"
            list.forEach { trans->

                result+= trans.amount.toString() + " | "+
                        trans.category.name + " | "+
                        trans.transactionType + " | "+
                        trans.date.toString() + "\n"
            }
            return result
        }
        return "Invalid Month"
    }
}
