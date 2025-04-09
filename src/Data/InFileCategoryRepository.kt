package src.Data

import Models.Category
import java.io.File

class InFileCategoryRepository(private val categoryFile:File):ICategoryRepository {

    override fun add(category: Category): Boolean {
        val categories = CategoryFileHelper.readCategories(categoryFile)
        categories.add(category)
        CategoryFileHelper.writeCategories(categoryFile, categories)
        return true
    }

    override fun delete(categoryNumber: Int): Boolean {
        val categories = CategoryFileHelper.readCategories(categoryFile)
        val categoriesSize = categories.size
        if (categoryNumber !in 1 .. categoriesSize)
            return false
        val isRemoved =  categories.remove(categories[categoryNumber - 1])

        CategoryFileHelper.writeCategories(categoryFile, categories)
        return isRemoved

    }

    override fun getAll(): List<Category> {
        return CategoryFileHelper.readCategories(categoryFile)
    }

}