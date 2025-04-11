package src.Services.Category

import Models.Category

fun listAllCategories(categoriesService: CategoryService) {
    val allCategories = categoriesService.getAllCategories()
    if (allCategories.isNotEmpty()) {
        allCategories.forEachIndexed { index, category ->
            println("${index + 1}) - ${category.name}")
        }
    } else {
        println("\nNo Categories found!")
    }
}

fun deleteCategory(categoryService: CategoryService) {
    val categories = categoryService.getAllCategories()
    if (categories.isEmpty()) {
        println("\nNo categories found!")
        return
    }
    listAllCategories(categoryService)
    print("\nChoose category: ")
    val option = readln().toIntOrNull()
    if (option != null && option in 1..categories.size) {
        val category = categories[option - 1]
        if (categoryService.deleteCategory(category)) {
            println("\nCategory deleted successfully")
        } else {
            println("\nCategory deletion failed")
        }
    } else {
        println("\nEnter valid choice!")
    }
}

fun addCategory(categoryService: CategoryService) {
    print("\nEnter category name: ")
    val catName = readln()
    val category = Category(catName)
    if (categoryService.addCategory(category)) {
        println("\nCategory added successfully")
    } else {
        println("\nCategory addition failed!")
    }
}