package lab2.client

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val password: String)
