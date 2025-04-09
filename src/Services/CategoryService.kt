package Services

import Models.Category
import src.Data.ICategoryRepository

class CategoryService(private val iCategoryRepository: ICategoryRepository) {

    fun addCategory(category: Category): Boolean {
        return iCategoryRepository.add(category)
    }

    fun deleteCategory(categoryNumber: Int): Boolean {
        return iCategoryRepository.delete(categoryNumber)
    }

    fun getAllCategories(): List<Category> {
        return iCategoryRepository.getAll()
    }



}