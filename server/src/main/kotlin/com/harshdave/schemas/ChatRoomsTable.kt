package com.harshdave.schemas

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object ChatRoomsTable : UUIDTable() {
    val created_at = datetime("created_at")
    val created_by = reference("created_by", UsersTable.id)
}