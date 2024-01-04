import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(val email: String, val userName: String, val password: String, val firstName: String, val lastName: String, val dob: String)
