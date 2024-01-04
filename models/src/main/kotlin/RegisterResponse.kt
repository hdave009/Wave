import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val statusCode: Int, val message: String)
