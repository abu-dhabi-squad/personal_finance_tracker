package Services

import Models.Category

class CategoryService {

    private val categories: MutableList<Category> = mutableListOf()

    fun addCategory(category: Category): Boolean {
        val trimmedCategory = category.name.trim()
        if (trimmedCategory.isEmpty()) return false

        trimmedCategory.lowercase().forEach {
            if (it !in 'a'..'z') return false
        }

        return categories.add(Category(trimmedCategory))
    }

    fun deleteCategory(index: Int): Boolean {
        val categoriesSize = categories.size
        if (index !in 1 .. categoriesSize)
            return false
        return categories.remove(categories[index - 1])

    }

    fun getAllCategories(): List<Category> {
        return categories
    }

    fun showCategoriesMenu(): String {
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

}