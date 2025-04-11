package src.Data

import Models.Category

class InMemoryCategory : CategoryInterface {
    private val categories: MutableList<Category> = mutableListOf()

    override fun add(category: Category): Boolean {
        return categories.add(category)
    }

    override fun delete(category: Category): Boolean {
        return categories.remove(category)
    }

    override fun getAll(): List<Category> {
        return categories.toList()
    }

}