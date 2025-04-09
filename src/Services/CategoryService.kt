package Services

import Models.Category
import src.Services.CategoryValidator

class CategoryService {

    private val categories: MutableList<Category> = mutableListOf()

    fun addCategory(category: Category): Boolean {

        val trimmedCategory = category.name.trim()
        val isValidCategory=CategoryValidator.isValidCategoryName(trimmedCategory)
        if (!isValidCategory)
            return false

        return categories.add(Category(trimmedCategory))
    }

    fun deleteCategory(index: Int): Boolean {
        val categoriesSize = categories.size
        if (index !in 1 .. categoriesSize)
            return false
        return categories.remove(categories[index - 1])

    }

    fun getAllCategories(): List<Category> {
        return categories.toList()
    }



}