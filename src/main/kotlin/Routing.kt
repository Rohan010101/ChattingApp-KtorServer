package com.example

import com.example.room.RoomController
import com.example.routes.chatSocket
import com.example.routes.getAllMessages
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import org.slf4j.event.*

fun Application.configureRouting() {
//    val roomController by inject<RoomController>()
    val roomController: RoomController by inject()


//    install(Routing)
    // incorrect in Ktor 2.x.


    routing {
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}
