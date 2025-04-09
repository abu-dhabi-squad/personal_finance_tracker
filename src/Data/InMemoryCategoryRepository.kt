package src.Data

import Models.Category

class InMemoryCategoryRepository: ICategoryRepository {
    private val categories: MutableList<Category> = mutableListOf()

    override fun add(category: Category): Boolean {
        val trimmedCategory = category.name.trim()
        if (trimmedCategory.isEmpty()) return false

        trimmedCategory.lowercase().forEach {
            if (it !in 'a'..'z') return false
        }

        return categories.add(Category(trimmedCategory))
    }

    override fun delete(categoryNumber: Int): Boolean {
        val categoriesSize = categories.size
        if (categoryNumber !in 1 .. categoriesSize)
            return false
        return categories.remove(categories[categoryNumber - 1])
    }

    override fun getAll(): List<Category> {
        return categories.toList()
    }

}