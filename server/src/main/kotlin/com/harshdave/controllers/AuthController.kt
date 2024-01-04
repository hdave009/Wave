package com.harshdave.controllers

import LoginRequest
import LoginResponse
import RegisterRequest
import RegisterResponse
import User
import com.harshdave.schemas.*
import java.time.LocalDateTime

class AuthController {

    fun login(loginRequest: LoginRequest): LoginResponse {

        try {
            // Ensure username exists
            if (!UsersTable.doesUserNameExist(loginRequest.userName)) {
                return LoginResponse(400, "Username Does Not Exist!", null)
            }

            val user = UsersTable.findUserByUsername(loginRequest.userName)
                ?: return LoginResponse(
                    500,
                    "There was a problem retrieving the user with username: ${loginRequest.userName}",
                    null
                )

            // Verify Password
            if (UsersTable.isValidPassword(loginRequest.password, user.hashed_password)) {
                user.hashed_password = ""
                return LoginResponse(200, "Successfully logged in!", user)
            } else {
                return LoginResponse(400, "Incorrect password!", null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return LoginResponse(500, "Something went wrong!", null)
        }
    }

    fun register(registerRequest: RegisterRequest): RegisterResponse {

        // Ensure Email is Unique
        if (UsersTable.doesEmailExist(registerRequest.email)) {
            return RegisterResponse(400, "Email already exists! Use a different email")
        }

        // Ensure Username is Unique
        if (UsersTable.doesUserNameExist(registerRequest.userName)) {
            return RegisterResponse(400, "Username already exists! Choose a different username!")
        }

        // Ensure No Field is Blank
        if (registerRequest.email.isBlank() || registerRequest.userName.isBlank() || registerRequest.firstName.isBlank() || registerRequest.lastName.isBlank() || registerRequest.password.isBlank() || registerRequest.dob.isBlank()) {
            return RegisterResponse(400, "No field must be left blank!")
        }

        // Ensure No Field is Blank
        if (!isValidDate(registerRequest.dob)) {
            return RegisterResponse(400, "Not a valid date")
        }

        try {
            val user = UsersTable.createUser(registerRequest.email, registerRequest.userName, UsersTable.hashPassword(registerRequest.password), registerRequest.firstName, registerRequest.lastName, registerRequest.dob)
            return if (user != null) {
                RegisterResponse(200, "Successfully created user: ${user.userName}!")
            } else {
                RegisterResponse(500, "Error Creating User!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return RegisterResponse(500, "Error Creating User!")
        }
    }
}

