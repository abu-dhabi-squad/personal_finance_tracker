package test

import Data.InFileTransactionImplementation
import Data.InMemoryTransactionImplementation
import Models.Category
import Models.SummaryTransactions
import Models.Transaction
import Models.TransactionType
import Services.ReportService
import Services.ReportValidatorImplementation
import src.Data.InFileCategory
import src.Data.InMemoryCategory
import java.io.File
import java.time.LocalDate
import java.util.*

class ReportTests{
    fun runAllTestCases(){
        println("\n\n${"#".repeat(10)} Report Tests ${"#".repeat(10)}\n\n")
        testGetBalanceInMemory()
        testGetBalanceInFile()
        testGetByMonthInMemory()
        testGetByMonthInFile()
        testgetByCategoryInMemory()
        testgetByCategoryInFile()
        testgetByTransactionTypeInMemory()
        testGetByTransactionTypeInFile()
    }

    private fun testGetBalanceInMemory(){
        println("\n${"*".repeat(10)} Test Get Balance In Memory ${"*".repeat(10)}\n")

        val inMemoryCategory = InMemoryCategory()
        val reportValidatorImplementation = ReportValidatorImplementation()
        var reportService = ReportService(InMemoryTransactionImplementation(),reportValidatorImplementation)
        test("empty list", reportService.getBalance(), 0.0)

        var memory = InMemoryTransactionImplementation()
        inMemoryCategory.add(Category("food",UUID.randomUUID().toString()))
        memory.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(memory,reportValidatorImplementation)
        test("add Income with 1000.0 to list", reportService.getBalance(), 1000.0)

        memory = InMemoryTransactionImplementation()
        memory.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(memory,reportValidatorImplementation)
        test("add Expenses with 1000.0 to list", reportService.getBalance(), 0.0)

        memory = InMemoryTransactionImplementation()
        memory.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(1800.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(memory,reportValidatorImplementation)
        test("add multiple transactions", reportService.getBalance(), 1800.0)
    }
    private fun testGetByMonthInMemory(){
        println("\n${"*".repeat(10)} Test Get By Month In Memory ${"*".repeat(10)}\n")

        val inMemoryCategory = InMemoryCategory()
        val reportValidatorImplementation = ReportValidatorImplementation()

        var reportService = ReportService(InMemoryTransactionImplementation(),reportValidatorImplementation)
        test("empty list", reportService.getSummaryByMonth(1, 2025), SummaryTransactions( 0.0, 0.0, listOf()))

        var memory = InMemoryTransactionImplementation()
        inMemoryCategory.add(Category("food",UUID.randomUUID().toString()))
        memory.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(200.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(memory,reportValidatorImplementation)
        test(
            "don't have the month in the list",
            reportService.getSummaryByMonth(1, 2025),
            SummaryTransactions(0.0, 0.0, listOf())
        )

        memory = InMemoryTransactionImplementation()
        memory.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(memory,reportValidatorImplementation)
        val caseResult = reportService.getSummaryByMonth(LocalDate.now().month.value, LocalDate.now().year).transactions.size
        test(
            "have 3 transactions in the list",
            caseResult,
            3
        )
        reportService = ReportService(memory,reportValidatorImplementation)
        test(
            "don't have the year in the list",
            reportService.getSummaryByMonth(1, 0),
            SummaryTransactions( 0.0, 0.0, listOf())
        )

        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        memory = InMemoryTransactionImplementation()
        val firstCategory = Category("food")
        val secondCategory = Category("food")
        memory.add(Transaction(100.0, firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1))
        memory.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2))
        reportService = ReportService(memory,reportValidatorImplementation)
        test(
            "have multiple months in list",
            reportService.getSummaryByMonth(1, 2024),
            SummaryTransactions(
                 100.0, 10.0, listOf(
                    Transaction(100.0,firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
                    Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2),
                )
            )
        )

        test("invalid month", reportService.getSummaryByMonth(13, 2025), SummaryTransactions( 0.0, 0.0, listOf()))
    }
    private fun testGetBalanceInFile(){
        println("\n${"*".repeat(10)} Test Get Balance In File ${"*".repeat(10)}\n")

        File("out/reportTestCategory.txt").delete()
        val inMemoryCategory = InFileCategory(File("out/reportTestCategory.txt"))
        val reportValidatorImplementation = ReportValidatorImplementation()
        File("out/reportTest.txt").delete()

        val file = InFileTransactionImplementation(File("out/reportTest.txt"))
        var reportService = ReportService(file,reportValidatorImplementation)
        inMemoryCategory.add(Category("food",UUID.randomUUID().toString()))
        test("empty list", reportService.getBalance(), 0.0)

        file.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(file,reportValidatorImplementation)
        test("add Income with 1000.0 to list", reportService.getBalance(), 1000.0)

        file.add(Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(file,reportValidatorImplementation)
        test("add Expenses with 1000.0 to list", reportService.getBalance(), 0.0)

        file.add(Transaction(1000.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(1000.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(1800.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(file,reportValidatorImplementation)
        test("add multiple transactions", reportService.getBalance(), 1800.0)

        File("out/reportTest.txt").delete()
        File("out/reportTestCategory.txt").delete()

    }
    private fun testGetByMonthInFile(){
        println("\n${"*".repeat(10)} Test Get By Month In File ${"*".repeat(10)}\n")

        File("out/reportTestCategory.txt").delete()
        val inFileCategory = InFileCategory(File("out/reportTestCategory.txt"))
        val reportValidatorImplementation = ReportValidatorImplementation()
        inFileCategory.add(Category("food",UUID.randomUUID().toString()))
        File("out/reportTest.txt").delete()
        val file = InFileTransactionImplementation(File("out/reportTest.txt"))
        var reportService = ReportService(file,reportValidatorImplementation)


        test("empty list", reportService.getSummaryByMonth(1, 2025), SummaryTransactions(0.0, 0.0, listOf()))


        file.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(200.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(file,reportValidatorImplementation)
        val caseResult = reportService.getSummaryByMonth(LocalDate.now().month.value, LocalDate.now().year).transactions.size
        test(
            "have 4 transactions in the list",
            caseResult,
            4
        )
        test(
            "don't have the month in the list",
            reportService.getSummaryByMonth(1, 2025),
            SummaryTransactions( 0.0, 0.0, listOf())
        )
        test(
            "don't have the year in the list",
            reportService.getSummaryByMonth(1, 0),
            SummaryTransactions( 0.0, 0.0, listOf())
        )

        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val firstCategory = Category("food")
        val secondCategory = Category("food")
        file.add(Transaction(100.0, firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1))
        file.add(Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2))
        reportService = ReportService(file,reportValidatorImplementation)
        test(
            "have multiple months in list",
            reportService.getSummaryByMonth(1, 2024),
            SummaryTransactions(
                 100.0, 10.0, listOf(
                    Transaction(100.0, firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
                    Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2),
                )
            )
        )

        test("invalid month", reportService.getSummaryByMonth(13, 2025), SummaryTransactions(0.0, 0.0, listOf()))
        File("out/reportTestCategory.txt").delete()
    }
    private fun testgetByCategoryInMemory(){
        println("\n${"*".repeat(10)} Test Get By Category In Memory ${"*".repeat(10)}\n")

        val inMemoryCategory = InMemoryCategory()
        val reportValidatorImplementation = ReportValidatorImplementation()

        var reportService = ReportService(InMemoryTransactionImplementation(),reportValidatorImplementation)
        test("empty list", reportService.getSummaryByCategory(Category("food")), SummaryTransactions( 0.0, 0.0, listOf()))

        var memory = InMemoryTransactionImplementation()
        memory.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(memory,reportValidatorImplementation)
        val caseResult = reportService.getSummaryByCategory(Category("food")).transactions.size
        test(
            "have 3 transactions in the list",
            caseResult,
            3
        )

        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        memory = InMemoryTransactionImplementation()
        inMemoryCategory.add(Category("beauty"))
        val firstCategory = Category("food")
        val secondCategory = Category("food")
        memory.add(Transaction(100.0, firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1))
        memory.add(Transaction(10.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(120.0, Category("beauty"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2))
        reportService = ReportService(memory,reportValidatorImplementation)
        test(
            "have multiple categories in list",
            reportService.getSummaryByCategory(Category("food")),
            SummaryTransactions(
                100.0, 10.0, listOf(
                    Transaction(100.0,firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
                    Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2),
                )
            )
        )
    }
    private fun testgetByCategoryInFile(){
        println("\n${"*".repeat(10)} Test Get By Category In File ${"*".repeat(10)}\n")

        File("out/reportTestCategory.txt").delete()
        val inFileCategory = InFileCategory(File("out/reportTestCategory.txt"))
        val reportValidatorImplementation = ReportValidatorImplementation()

        var reportService = ReportService(InMemoryTransactionImplementation(),reportValidatorImplementation)
        test("empty list", reportService.getSummaryByCategory(Category("food")), SummaryTransactions( 0.0, 0.0, listOf()))

        File("out/reportTest.txt").delete()
        var file = InFileTransactionImplementation(File("out/reportTest.txt"))
        inFileCategory.add(Category("food"))
        file.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(10.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(200.0, Category("food"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(file,reportValidatorImplementation)
        val caseResult = reportService.getSummaryByCategory(Category("food")).transactions.size
        test(
            "have 4 transactions in the list",
            caseResult,
            4
        )

        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        File("out/reportTest.txt").delete()
        file = InFileTransactionImplementation(File("out/reportTest.txt"))
        inFileCategory.add(Category("beauty"))
        val firstCategory = Category("food")
        val secondCategory = Category("food")
        file.add(Transaction(100.0, firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1))
        file.add(Transaction(10.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(120.0, Category("beauty"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2))
        reportService = ReportService(file,reportValidatorImplementation)
        test(
            "have multiple categories in list",
            reportService.getSummaryByCategory(Category("food")),
            SummaryTransactions(
                100.0, 10.0, listOf(
                    Transaction(100.0,firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
                    Transaction(10.0, secondCategory, TransactionType.EXPENSES, LocalDate.of(2024, 1, 13), id2),
                )
            )
        )
        File("out/reportTestCategory.txt").delete()
        File("out/reportTest.txt").delete()
    }
    private fun testgetByTransactionTypeInMemory(){
        println("\n${"*".repeat(10)} Test Get By Transaction Type In Memory ${"*".repeat(10)}\n")

        val inMemoryCategory = InMemoryCategory()
        val reportValidatorImplementation = ReportValidatorImplementation()

        var reportService = ReportService(InMemoryTransactionImplementation(),reportValidatorImplementation)
        test("empty list", reportService.getSummaryByType(TransactionType.INCOME), SummaryTransactions( 0.0, 0.0, listOf()))

        var memory = InMemoryTransactionImplementation()
        memory.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(10.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(memory,reportValidatorImplementation)
        val caseResult = reportService.getSummaryByType(TransactionType.INCOME).transactions.size
        test(
            "have 3 same transaction type in the list",
            caseResult,
            3
        )

        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        memory = InMemoryTransactionImplementation()
        inMemoryCategory.add(Category("beauty"))
        val firstCategory = Category("food")
        val secondCategory = Category("food")
        memory.add(Transaction(100.0, firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1))
        memory.add(Transaction(10.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(120.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        memory.add(Transaction(10.0, secondCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 13), id2))
        reportService = ReportService(memory,reportValidatorImplementation)
        test(
            "have multiple Transaction Type in list",
            reportService.getSummaryByType(TransactionType.INCOME),
            SummaryTransactions(
                110.0, 0.0, listOf(
                    Transaction(100.0,firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
                    Transaction(10.0, secondCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 13), id2),
                )
            )
        )
    }
    private fun testGetByTransactionTypeInFile(){
        println("\n${"*".repeat(10)} Test Get By Transaction Type In File ${"*".repeat(10)}\n")

        File("out/reportTestCategory.txt").delete()
        val inFileCategory = InFileCategory(File("out/reportTestCategory.txt"))
        val reportValidatorImplementation = ReportValidatorImplementation()

        var reportService = ReportService(InMemoryTransactionImplementation(),reportValidatorImplementation)

        File("out/reportTest.txt").delete()
        var file = InFileTransactionImplementation(File("out/reportTest.txt"))
        inFileCategory.add(Category("food"))
        file.add(Transaction(100.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(10.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(120.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(200.0, Category("food"), TransactionType.INCOME, LocalDate.now(), UUID.randomUUID()))
        reportService = ReportService(file,reportValidatorImplementation)
        test(
            "don't have the type in the list",
            reportService.getSummaryByType(TransactionType.EXPENSES),
            SummaryTransactions(0.0, 0.0, listOf())
        )

        reportService = ReportService(file,reportValidatorImplementation)
        val caseResult = reportService.getSummaryByType(TransactionType.INCOME).transactions.size
        test(
            "have 4 transactions in the list",
            caseResult,
            4
        )

        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        File("out/reportTest.txt").delete()
        file = InFileTransactionImplementation(File("out/reportTest.txt"))
        inFileCategory.add(Category("beauty"))
        val firstCategory = Category("food")
        val secondCategory = Category("food")
        file.add(Transaction(100.0, firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1))
        file.add(Transaction(10.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(120.0, Category("beauty"), TransactionType.EXPENSES, LocalDate.now(), UUID.randomUUID()))
        file.add(Transaction(10.0, secondCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 13), id2))
        reportService = ReportService(file,reportValidatorImplementation)
        test(
            "have multiple transactions types in list",
            reportService.getSummaryByType(TransactionType.INCOME),
            SummaryTransactions(
                110.0, 0.0, listOf(
                    Transaction(100.0,firstCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 12), id1),
                    Transaction(10.0, secondCategory, TransactionType.INCOME, LocalDate.of(2024, 1, 13), id2),
                )
            )
        )
        File("out/reportTestCategory.txt").delete()
        File("out/reportTest.txt").delete()
    }










}