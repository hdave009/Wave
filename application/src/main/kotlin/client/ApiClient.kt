package client

import ENVIRONMENT
import LoginRequest
import LoginResponse
import RegisterRequest
import RegisterResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ApiClient {

    // Base URL for the API
    private val baseUrl = if (ENVIRONMENT != "remote") "http://127.0.0.1:8080" else ""


    // HTTP client used for making requests
    private val client = HttpClient()


    // Function to send a login request to the server
    suspend fun loginRequest(userName: String, password: String): LoginResponse {
        val url = "$baseUrl/auth/login"

        // Create a LoginRequest object with email and password
        val loginRequest = LoginRequest(userName, password)

        return try{
            // Send a POST request with the login request
            val response = client.post(url) {
                setBody(Json.encodeToString(loginRequest))
            }

            // Decode the response to get the login token
            Json.decodeFromString<LoginResponse>(response.body())
        } catch(e: Exception) {
            // Handle exceptions (e.g., invalid credentials)
           LoginResponse(400, "Client Side: Error making Request", null)
        }
    }

    // Function to send a registration request to the server
    suspend fun registerRequest(email: String, userName: String, password: String, firstName: String, lastName: String, dob: String): RegisterResponse {
        val url = "$baseUrl/auth/register"

        // Create a LoginRequest object with email and password
        val registerRequest = RegisterRequest(email, userName, password, firstName, lastName, dob)

        return try{
            // Send a POST request with the registration request
            val response = client.post(url) {
                setBody(Json.encodeToString(registerRequest))
            }
            // Decode the response to get the login token
            Json.decodeFromString<RegisterResponse>(response.body())
        } catch(e: Exception) {
            RegisterResponse(400, "Client Side: Error making Request")
        }
    }

}
