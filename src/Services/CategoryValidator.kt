package src.Services

import Models.Category

object CategoryValidator {


    fun isValidCategoryName(categoryName: String):Boolean{

        if (categoryName.isEmpty()) return false
        categoryName.lowercase().forEach {
            if (it !in 'a'..'z' && it != '_') return false
        }
        return true
    }
}