package src.Data.Category

import Models.Category

interface CategoryInterface {

    fun add(category: Category):Boolean
    fun delete(category: Category):Boolean
    fun getAll():List<Category>

}