package com.talamyd

import com.talamyd.database.DatabaseFactory
import com.talamyd.di.configureDI
import com.talamyd.plugins.configureRouting
import com.talamyd.plugins.configureSecurity
import com.talamyd.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureDI()
    configureSecurity()
    configureRouting()
}
