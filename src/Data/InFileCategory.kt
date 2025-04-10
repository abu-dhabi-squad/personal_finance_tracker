package src.Data

import Models.Category
import java.io.File

class InFileCategory(private val categoryFile: File) : CategoryInterface {

    override fun add(category: Category): Boolean {
        val categories = CategoryFileHelper.readCategories(categoryFile)
        val isCategoryAdded= categories.add(category)
        if (isCategoryAdded){
            CategoryFileHelper.writeCategories(categoryFile, categories)
        }
        return isCategoryAdded
    }

    override fun delete(category: Category): Boolean {
        val categories = CategoryFileHelper.readCategories(categoryFile)
        val isRemoved = categories.removeIf { it.name == category.name }
        if (isRemoved) {
            CategoryFileHelper.writeCategories(categoryFile, categories)
        }
        return isRemoved
    }

    override fun getAll(): List<Category> {
        return CategoryFileHelper.readCategories(categoryFile)
    }
}