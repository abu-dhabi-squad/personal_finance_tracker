package src.Utils

class CategoryValidator : CategoryValidatorInterface {

    override fun isValidCategoryName(categoryName: String): Boolean {
        return categoryName.isNotEmpty()
    }
}