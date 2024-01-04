import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val statusCode: Int, val message: String, val user: User?)
