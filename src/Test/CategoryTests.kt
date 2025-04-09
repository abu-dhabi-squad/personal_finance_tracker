package Test

import Models.Category
import Services.CategoryService
import src.Data.InMemoryCategoryRepository

fun main() {
    val categoryTest = CategoryTests()
    categoryTest.testAddCategory()
}
class CategoryTests {


    fun testAddCategory() {
        val category = CategoryService(InMemoryCategoryRepository())

        test("check add new category", true,
            category.addCategory(Category("Shopping")))

        test("check if a new category is empty", false,
            category.addCategory(Category("")))

        test("check if the category has an invalid char", false,
            category.addCategory(Category("Re^%nt")))

    }


    fun testDeleteCategory() {
        val category: CategoryService = CategoryService(InMemoryCategoryRepository())
        val categorySize=category.getAllCategories().size

        test("check if the category deleted", true,
            category.deleteCategory(0))

        test("check if the index is out of range", false,
            category.deleteCategory(categorySize))

    }

    //Todo
    fun testGetAllCategory() {
        //val category: CategoryService = CategoryService()


    }
}