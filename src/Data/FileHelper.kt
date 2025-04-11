package Data

import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

object FileHelper {

    @Suppress("UNCHECKED_CAST")
    fun <T : Serializable> readList(file: File): MutableList<T> {
        if (!file.exists()) return mutableListOf()
        return try {
            ObjectInputStream(file.inputStream()).use {
                it.readObject() as? MutableList<T> ?: mutableListOf()
            }
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    fun <T : Serializable> writeList(file: File, list: List<T>) {
        ObjectOutputStream(file.outputStream()).use {
            it.writeObject(list)
        }
    }
}
