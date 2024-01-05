package com.harshdave.schemas

import org.jetbrains.exposed.dao.id.IntIdTable

object RoomsToUsersTable : IntIdTable("RoomsToUsers") {
    val roomId = reference("room_id", ChatRoomsTable.id)
    val userId = reference("user_id", UsersTable.id)
}
