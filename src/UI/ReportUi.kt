package UI

import Services.ReportService

fun getMonthlyReport(reportService: ReportService) {
    print("please enter the month [1-12]: ")
    val month = readln().toIntOrNull()
    if (month == null) {
        println("invalid month")
        return
    }
    println(getMonthlySummaryFormat(reportService, month))
}

private fun getMonthlySummaryFormat(reportService: ReportService, month: Int): String {
    val list = reportService.getSummaryByMonth(month)
    if (list.isEmpty()) {
        return "no transaction in this month"
    }
    var result = "total transaction in month = " + list.size + "\n"
    result += "amount | category | transactionType | date\n"
    list.forEach { trans ->
        result += trans.amount.toString() + " | " +
                trans.category.name + " | " +
                trans.transactionType + " | " +
                trans.date.toString() + "\n"
    }
    return result
}

fun getBalance(reportService: ReportService) {
    println("the Balance = " + reportService.getBalance())
}