
import Models.Category
import Services.CategoryService
import src.Data.InFileCategory
import src.Data.InMemoryCategory
import src.Utils.CategoryValidatorName
import java.io.File

fun main() {
    val categoryTest = CategoryTests()

    categoryTest.testAddCategoryInMemory()
    categoryTest.testDeleteCategoryInMemory()
    categoryTest.testGetAllCategoryInMemory()

    categoryTest.testAddCategoryInFile()
    categoryTest.testDeleteCategoryInFile()
    categoryTest.testGetAllCategoryInFile()
}

class CategoryTests {
    fun testAddCategoryInMemory() {
        val categoryService = CategoryService(InMemoryCategory(), CategoryValidatorName())
        test(
            "check add new category", true,
            categoryService.addCategory(Category("Shopping"))
        )
        test(
            "check if a new category is empty", false,
            categoryService.addCategory(Category(""))
        )
    }

    fun testDeleteCategoryInMemory() {
        val categoryService: CategoryService = CategoryService(InMemoryCategory(), CategoryValidatorName())
        val category = Category("Shopping")
        categoryService.addCategory(category)
        test(
            "check if the category deleted", true,
            categoryService.deleteCategory(Category("Shopping"))
        )
    }

    fun testGetAllCategoryInMemory() {
        val categoryService: CategoryService = CategoryService(InMemoryCategory(), CategoryValidatorName())
        categoryService.addCategory(Category("Rent"))
        val testList = listOf(Category("Rent"))
        val result = categoryService.getAllCategories() == testList
        test(
            "check if Category service return a valid list", false,
            result
        )
    }

    fun testAddCategoryInFile() {
        val categoryService = CategoryService(InFileCategory(File(CATEGORY_FILE_NAME)), CategoryValidatorName())
        test(
            "check add new category", true,
            categoryService.addCategory(Category("Shopping"))
        )
        test(
            "check if a new category is empty", false,
            categoryService.addCategory(Category(""))
        )
    }

    fun testGetAllCategoryInFile() {
        val categoryService: CategoryService =
            CategoryService(InFileCategory(File(CATEGORY_FILE_NAME)), CategoryValidatorName())
        categoryService.addCategory(Category("Rent"))
        val testList = listOf(Category("Rent"))
        val result = categoryService.getAllCategories() == testList
        test(
            "check if Category service return a valid list", false,
            result
        )
    }

    fun testDeleteCategoryInFile() {
        val categoryService: CategoryService =
            CategoryService(InFileCategory(File(CATEGORY_FILE_NAME)), CategoryValidatorName())
        val category = Category("Shopping")
        categoryService.addCategory(category)
        test(
            "check if the category deleted", true,
            categoryService.deleteCategory(Category("Shopping"))
        )
    }

    companion object {
        const val CATEGORY_FILE_NAME = "category"
    }
}