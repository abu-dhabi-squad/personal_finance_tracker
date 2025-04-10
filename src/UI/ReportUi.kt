package UI

import Services.ReportService

fun getMonthlyReport(reportService: ReportService) {
    print("please enter the month [1-12]: ")
    val month = readln().toIntOrNull()
    if (month == null) {
        println("invalid month")
        return
    }
    print("please enter the year: ")
    val year = readln().toIntOrNull()
    if (year == null) {
        println("invalid year")
        return
    }

    println(getMonthlySummaryFormat(reportService, month, year))
}

private fun getMonthlySummaryFormat(reportService: ReportService, month: Int, year: Int): String {
    val monthTransactions = reportService.getSummaryByMonth(month, year)
    if (monthTransactions.transactions.isEmpty()) {
        return "no transaction in this date"
    }
    var result = "month = " + monthTransactions.month + "\nyear = " + monthTransactions.year + "\n"
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

fun getBalance(reportService: ReportService) {
    println("the Balance = " + reportService.getBalance())
}