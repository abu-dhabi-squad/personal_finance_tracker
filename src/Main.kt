import Models.Category

fun main() {

}

    fun showCategoriesMenu(categories: List<Category>): String {
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