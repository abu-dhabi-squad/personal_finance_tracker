package src.Data

import Models.Category
import java.io.File

class InFileCategory(private val categoryFile: File) : CategoryInterface {

    override fun add(category: Category): Boolean {
        val categories = CategoryFileHelper.readCategories(categoryFile)
        categories.add(category)
        CategoryFileHelper.writeCategories(categoryFile, categories)
        return true
    }

    override fun delete(category: Category): Boolean {
        val categories = CategoryFileHelper.readCategories(categoryFile)
        val isRemoved = categories.removeIf { it.name == category.name }
        CategoryFileHelper.writeCategories(categoryFile, categories)
        return isRemoved
    }

    override fun getAll(): List<Category> {
        return CategoryFileHelper.readCategories(categoryFile)
    }
}