package com.harshdave.plugins

import User
import com.harshdave.schemas.UsersTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val database = Database.connect(
            url = "jdbc:sqlite:database.sqlite",
            user = "admin",
            driver = "org.sqlite.JDBC",
            password = "admin"
    )

    transaction {
        SchemaUtils.create(UsersTable)
    }
}
