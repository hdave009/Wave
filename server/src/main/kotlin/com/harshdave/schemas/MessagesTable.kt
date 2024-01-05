package com.harshdave.schemas

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object MessagesTable : UUIDTable() {
    val sender = reference("sender_id", UsersTable.id)
    val room = reference("room_id", ChatRoomsTable.id)
    val sent_at = datetime("sent_at")
    val message = text("message")
}