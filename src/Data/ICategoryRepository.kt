package src.Data

import Models.Category

interface ICategoryRepository {

    fun add(category: Category):Boolean
    fun delete(index: Int):Boolean
    fun getAll():List<Category>

}