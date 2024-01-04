package com.harshdave.plugins

import com.harshdave.routes.authRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRoutes()
    }
}
