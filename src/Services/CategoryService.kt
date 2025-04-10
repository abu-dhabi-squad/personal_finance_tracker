package Services

import Models.Category
import src.Data.CategoryInterface
import src.Util.CategoryValidatorInterface

class CategoryService(private val categoryInterface: CategoryInterface, private val categoryValidatorInterface: CategoryValidatorInterface) {

    fun addCategory(category: Category): Boolean {
        val isValidCategory= categoryValidatorInterface.isValidCategoryName(category.name)
        if (!isValidCategory) return false
        if (isCategoryExist(category)) return false
        return categoryInterface.add(category)
    }

    fun deleteCategory(category: Category): Boolean {
        if (category.name.isEmpty()) return false
        if (!isCategoryExist(category)) return false
        return categoryInterface.delete(category)
    }

    fun getAllCategories(): List<Category> {
        return categoryInterface.getAll()
    }

    private fun isCategoryExist(category: Category): Boolean {
        val filteredCategories = categoryInterface.getAll().filter {
            it.name.trim().equals(category.name.trim(), true) }
        return filteredCategories.isNotEmpty()
    }
}