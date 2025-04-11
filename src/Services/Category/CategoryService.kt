package src.Services.Category

import Models.Category
import src.Data.Category.CategoryInterface

class CategoryService(
    private val categoryImp: CategoryInterface,
    private val categoryValidatorInterface: CategoryValidatorInterface
) {

    fun addCategory(category: Category): Boolean {
        val isValidCategory = categoryValidatorInterface.isValidCategoryName(category.name)
        if (!isValidCategory) return false
        if (isCategoryExist(category)) return false
        return categoryImp.add(category)
    }

    fun deleteCategory(category: Category): Boolean {
        val isValidCategory = categoryValidatorInterface.isValidCategoryName(category.name)
        if (!isValidCategory) return false
        if (!isCategoryExist(category)) return false
        return categoryImp.delete(category)
    }

    fun getAllCategories(): List<Category> {
        return categoryImp.getAll()
    }

    private fun isCategoryExist(category: Category): Boolean {
        return categoryImp.getAll().any {
            it.name.trim().equals(category.name.trim(), true)
        }
    }
}