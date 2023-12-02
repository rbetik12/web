package lab2.client

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long = 0,
    val name: String = "",
    val producedBy: String = "",
    val price: Float = 0.0f,
    val sellAmount: Long = 0
)
