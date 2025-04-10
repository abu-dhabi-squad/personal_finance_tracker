package Models

import java.io.Serializable
import java.util.UUID

data class Category(
    val name: String,
    val id: String = UUID.randomUUID().toString()
) : Serializable