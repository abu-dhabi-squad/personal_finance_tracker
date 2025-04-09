package src.Data

import Models.Category

interface ICategoryRepository {

    fun add(category: Category):Boolean
    fun delete(categoryNumber: Int):Boolean
    fun getAll():List<Category>

}