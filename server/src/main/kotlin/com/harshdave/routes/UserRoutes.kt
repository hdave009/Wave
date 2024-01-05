package com.harshdave.routes

import LoginRequest
import LoginResponse
import RegisterRequest
import User
import com.harshdave.controllers.AuthController
import com.harshdave.controllers.UsersController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Routing.userRoutes() {

    val userController = UsersController()

    route("/user") {
        get("/search") {
            val substring = call.request.queryParameters["query"]
            if (substring.isNullOrBlank()) {
                call.respond(Json.encodeToString(emptyList<User>()))
            }
            val users = userController.getAllUsersBySearch(substring!!)
            call.respond(Json.encodeToString(users))
        }
    }


}
