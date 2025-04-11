package src.Services.Report

fun getMonthlyReport(reportService: ReportService) {
    print("\nEnter month [1-12]: ")
    val month = readln().toIntOrNull()
    if (month == null) {
        println("\ninvalid month!")
        return
    }
    print("\nEnter year: ")
    val year = readln().toIntOrNull()
    if (year == null) {
        println("\ninvalid year")
        return
    }

    println(getMonthlySummaryFormat(reportService, month, year))
}

fun getBalance(reportService: ReportService) {
    println("the Balance = " + reportService.getBalance())
}

private fun getMonthlySummaryFormat(reportService: ReportService, month: Int, year: Int): String {
    val monthTransactions = reportService.getSummaryByMonth(month, year)
    if (monthTransactions.transactions.isEmpty()) {
        return "\nNo transaction in this date"
    }
    var result = "\nmonth = " + monthTransactions.month + "\nyear = " + monthTransactions.year + "\n"
    result += "total transaction in month = " + monthTransactions.transactions.size + "\n"
    result += "total income in month = " + monthTransactions.totalIncome + "\n"
    result += "total expenses in month = " + monthTransactions.totalExpenses + "\n\n"
    result += String.format("%-18s | %-18s  | %-18s  | %-18s  |\n", "amount", "category", "transaction Type", "date")
    monthTransactions.transactions.forEach { trans ->
        result += String.format("%-18s |", trans.amount.toString()) + "  " +
                String.format("%-18s |", trans.category.name) + "  " +
                String.format("%-18s |", trans.transactionType.name) + "  " +
                String.format("%-18s |", trans.date.toString()) + "\n"
    }
    return result
}