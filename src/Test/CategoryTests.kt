import Models.Category
import Services.CategoryService
import Test.test
import src.Data.InFileCategory
import src.Data.InMemoryCategory
import src.Utils.CategoryValidator
import java.io.File

class CategoryTests {
    fun testAddCategoryInMemory() {
        val categoryService = CategoryService(InMemoryCategory(), CategoryValidator())
        test(
            "check add new category in memory", true,
            categoryService.addCategory(Category("Shopping"))
        )
        test(
            "check if a new category is empty in memory", false,
            categoryService.addCategory(Category(""))
        )
    }

    fun testDeleteCategoryInMemory() {
        var categoryService = CategoryService(InMemoryCategory(), CategoryValidator())
        val category = Category("Shopping")
        categoryService.addCategory(category)

        test(
            "check if the category deleted in memory", true,
            categoryService.deleteCategory(Category("Shopping"))
        )
        categoryService = CategoryService(InMemoryCategory(), CategoryValidator())
        test(
            "check if invalid delete operation in memory", false,
            categoryService.deleteCategory(Category("Shopping"))
        )
    }

    fun testGetAllCategoryInMemory() {
        var categoryService: CategoryService = CategoryService(InMemoryCategory(), CategoryValidator())
        var category = Category("Rent")
        categoryService.addCategory(category)
        var testList = listOf(category)
        var result = categoryService.getAllCategories() == testList
        test(
            "check if Category service return a valid list in memory", true,
            result
        )
        categoryService = CategoryService(InMemoryCategory(), CategoryValidator())
        category = Category("Rent")
        categoryService.addCategory(category)
        testList = emptyList()
        result = categoryService.getAllCategories() == testList
        test(
            "check if Category service return an invalid list in memory", false,
            result
        )
    }

    fun testAddCategoryInFile() {
        val categoryService = CategoryService(InFileCategory(File("out/category file")), CategoryValidator())
        test(
            "check add new category in file", true,
            categoryService.addCategory(Category("Shopping"))
        )
        test(
            "check if a new category is empty in file", false,
            categoryService.addCategory(Category(""))
        )
    }

    fun testGetAllCategoryInFile() {
        val categoryService = CategoryService(InFileCategory(File("out/${System.currentTimeMillis()}")), CategoryValidator())
        var category = Category("Rent")
        categoryService.addCategory(category)
        var testList = listOf(category)
        var result = categoryService.getAllCategories() == testList
        test(
            "check if Category service return a valid list in file", true,
            result
        )
        val categoryService2 = CategoryService(InFileCategory(File("out/Test Get all")), CategoryValidator())
        category = Category("Rent")
        categoryService2.addCategory(category)
        testList = listOf(Category("sTest"))
        result = categoryService2.getAllCategories() == testList
        test(
            "check if Category service return an invalid list in file", false,
            result
        )
    }

    fun testDeleteCategoryInFile() {
        var categoryService: CategoryService =
            CategoryService(InFileCategory(File("out/${System.currentTimeMillis()}")), CategoryValidator())
        val category = Category("Shopping")
        categoryService.addCategory(category)
        test(
            "check if the category deleted in file", true,
            categoryService.deleteCategory(Category("Shopping"))
        )
        categoryService = CategoryService(InMemoryCategory(), CategoryValidator())
        test(
            "check if invalid delete operation in file", false,
            categoryService.deleteCategory(Category("Shopping"))
        )
    }

    fun runAllTestCases(){
        println("\n${"*".repeat(10)} Test Categories ${"*".repeat(10)}\n")
        testAddCategoryInMemory()
        testDeleteCategoryInMemory()
        testGetAllCategoryInMemory()
        testAddCategoryInFile()
        testGetAllCategoryInFile()
        testDeleteCategoryInFile()
    }

}