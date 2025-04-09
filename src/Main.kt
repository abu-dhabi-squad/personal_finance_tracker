import Models.Category
import Services.CategoryService
import src.Data.InFileCategoryRepository
import src.Data.InMemoryCategoryRepository
import java.io.File

fun main() {

    //val categoryServiceInMemory=CategoryService(InMemoryCategoryRepository())

    val categoryServiceInFile=CategoryService(InFileCategoryRepository(File("category")))

//    categoryService.addCategory(Category("Shopping"))
//    categoryService.addCategory(Category("Shopping"))
//    categoryService.addCategory(Category("Shopping"))
    categoryServiceInFile.addCategory(Category("Shopping"))
    categoryServiceInFile.addCategory(Category("rent"))
    println(categoryServiceInFile.getAllCategories())


    println(showCategoriesMenu(categoryServiceInFile.getAllCategories()))


}

    fun showCategoriesMenu(categories: List<Category>): String {
        var menuText = "categories: -\n"
        if (categories.isNotEmpty()) {

            categories.forEachIndexed { index, category ->
                menuText += "${index + 1}) ${category.name}\n"
            }
        }
        else {
            menuText+="The category is empty!"
        }
        return menuText
    }

