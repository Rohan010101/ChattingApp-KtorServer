package com.example

import com.example.session.ChatSession
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.websocket.*
import org.slf4j.event.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }

    intercept(Plugins) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"] ?: "Guest"
            call.sessions.set(ChatSession(username, generateNonce()))
        }
    }

//    routing {
//        get("/session/increment") {
//            val session = call.sessions.get<MySession>() ?: MySession()
//            call.sessions.set(session.copy(count = session.count + 1))
//            call.respondText("Counter is ${session.count}. Refresh to increment.")
//        }
//    }
}
//@Serializable
//data class MySession(val count: Int = 0)
