package com.talamyd.plugins

import com.talamyd.route.authRouting
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        authRouting()
    }
}
