package src.Services.Category

interface CategoryValidatorInterface {
    fun isValidCategoryName(categoryName: String): Boolean
}