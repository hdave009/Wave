package com.harshdave.routes

import LoginRequest
import LoginResponse
import RegisterRequest
import com.harshdave.controllers.AuthController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Routing.authRoutes() {

    val authController = AuthController()

    route("/auth") {
        post("/login") {
            val loginRequest = Json.decodeFromString<LoginRequest>(call.receiveText())
            val response = authController.login(loginRequest)
            call.respond(HttpStatusCode(response.statusCode, response.message), response)
        }

        post("/register") {
            val registerRequest = Json.decodeFromString<RegisterRequest>(call.receiveText())
            val response = authController.register(registerRequest)
            call.respond(HttpStatusCode(response.statusCode, response.message), response)
        }
    }


}
