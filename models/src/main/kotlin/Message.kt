import kotlinx.serialization.Serializable

@Serializable
data class Message(val userName: String, val message: String)