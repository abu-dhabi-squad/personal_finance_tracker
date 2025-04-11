package src.Services.Category

class CategoryValidator : CategoryValidatorInterface {

    override fun isValidCategoryName(categoryName: String): Boolean {
        return categoryName.isNotEmpty()
    }
}