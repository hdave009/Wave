package com.harshdave.schemas

import User
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

object UsersTable : UUIDTable() {

    val email = varchar("email", 255).uniqueIndex()
    val hashedPassword = varchar("hashed_password", 64)
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val userName = varchar("user_name", 50).uniqueIndex()
    val dateOfBirth = date("date_of_birth")
    val created_at = datetime("created_at")
}

fun UsersTable.findUserByUsername(userName: String): User? {
    var userRow : ResultRow? = null
    transaction {
        userRow = UsersTable.select { UsersTable.userName eq userName }.singleOrNull()
    }

    return userRow?.let { toUser(it) }
}

fun UsersTable.isValidPassword(inputPassword : String, storedHashedPassword: String): Boolean {
    return BCrypt.checkpw(inputPassword, storedHashedPassword)
}

fun UsersTable.hashPassword(password: String) : String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
}

fun UsersTable.createUser(email: String, userName: String, hashedPassword: String, firstName: String, lastName: String, dob: String) : User? {
    var userRow : ResultRow? = null
    transaction {
        val generatedKey = UsersTable.insert {
            it[UsersTable.email] = email
            it[UsersTable.userName] = userName
            it[UsersTable.hashedPassword] = hashedPassword
            it[UsersTable.firstName] = firstName
            it[UsersTable.lastName] = lastName
            it[dateOfBirth] = LocalDate.parse(dob)
            it[created_at] = LocalDateTime.now()
        } get UsersTable.id

        userRow = UsersTable.select { UsersTable.id eq generatedKey }.singleOrNull()
    }

    return userRow?.let { toUser(it) }
}

fun UsersTable.doesEmailExist(email: String): Boolean {
    return transaction {
        UsersTable.select { UsersTable.email eq email }.count() > 0
    }
}

fun UsersTable.doesUserNameExist(userName: String): Boolean {
    return transaction {
        UsersTable.select { UsersTable.userName eq userName }.count() > 0
    }
}

fun isValidDate(date: String): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        LocalDate.parse(date, formatter)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}


fun toUser(row: ResultRow): User =
    User(
        userId = row[UsersTable.id].value.toString(),
        email = row[UsersTable.email],
        hashed_password = row[UsersTable.hashedPassword],
        firstName = row[UsersTable.firstName],
        lastName = row[UsersTable.lastName],
        userName = row[UsersTable.userName],
        dob = row[UsersTable.dateOfBirth].toString(),
        created_at = row[UsersTable.created_at].toString()
    )

