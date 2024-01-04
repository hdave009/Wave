import kotlinx.serialization.Serializable

@Serializable
data class User(val userId: String, val email: String, val userName: String, val firstName: String, val lastName: String, var hashed_password: String, val dob: String, val created_at: String)