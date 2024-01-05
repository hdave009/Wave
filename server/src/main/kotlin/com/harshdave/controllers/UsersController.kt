package com.harshdave.controllers

import User
import com.harshdave.schemas.UsersTable
import com.harshdave.schemas.getAllUsersBySearch

class UsersController {

    fun getAllUsersBySearch(substring: String) : List<User> {
        return UsersTable.getAllUsersBySearch(substring)
    }

}