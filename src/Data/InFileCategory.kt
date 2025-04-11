package src.Data

import Utils.FileHelper
import Models.Category
import java.io.File

class InFileCategory(private val categoryFile: File) : CategoryInterface {

    override fun add(category: Category): Boolean {
        val categories = FileHelper.readList<Category>(categoryFile)
        val isCategoryAdded= categories.add(category)
        if (isCategoryAdded){
            FileHelper.writeList(categoryFile, categories)
        }
        return isCategoryAdded
    }

    override fun delete(category: Category): Boolean {
        val categories = FileHelper.readList<Category>(categoryFile)
        val isRemoved = categories.remove(category)
        if (isRemoved) {
            FileHelper.writeList(categoryFile, categories)
        }
        return isRemoved
    }

    override fun getAll(): List<Category> {
        return FileHelper.readList(categoryFile)
    }
}