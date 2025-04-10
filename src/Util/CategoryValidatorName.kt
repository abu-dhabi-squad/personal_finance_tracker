package src.Util

class CategoryValidatorName : CategoryValidatorInterface{

    override fun isValidCategoryName(categoryName: String): Boolean {
        return categoryName.isNotEmpty()
    }
}