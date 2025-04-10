package src.Utils

class CategoryValidatorName : CategoryValidatorInterface {

    override fun isValidCategoryName(categoryName: String): Boolean {
        return categoryName.isNotEmpty()
    }
}