package com.example.room

import com.example.data.MessageDataSource
import com.example.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {

    private val members = ConcurrentHashMap<String, Member>()

    // On Joining the Room
    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if(members.containsKey(username)) {
            throw MemberAlreadyExistsException()
        }
        members[username] = Member(
            username = username,
            sessionId = sessionId,
            socket = socket
        )
    }


    // SEND MESSAGE
    suspend fun sendMessage(
        senderUsername: String,
        message: String
    ) {
        members.values.forEach { member ->
            val messageEntity = Message(
                text = message,
                username = senderUsername,
                timestamp = System.currentTimeMillis()
            )

            // Inserts into DB
            messageDataSource.insertMessage(messageEntity)

            // Sends the Message to the room via Web Socket
            val parsedMessage = Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parsedMessage))
        }
    }


    // FETCH ALL THE MESSAGES
    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }


    // DISCONNECTING FROM THE ROOM
    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if(members.containsKey(username)) {
            members.remove(username)
        }
    }
}