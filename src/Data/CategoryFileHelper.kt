//package src.Data
//
//import Models.Category
//import java.io.File
//import java.io.ObjectInputStream
//import java.io.ObjectOutputStream
//
//object CategoryFileHelper {
//
//    fun readCategories(file: File): MutableList<Category> {
//        if (!file.exists()) return mutableListOf()
//        return try {
//            ObjectInputStream(file.inputStream()).use {
//                it.readObject() as MutableList<Category>
//            }
//        } catch (e: Exception) {
//            mutableListOf()
//        }
//    }
//
//    fun writeCategories(file: File, categories: MutableList<Category>) {
//        ObjectOutputStream(file.outputStream()).use {
//            it.writeObject(categories)
//        }
//    }
//}