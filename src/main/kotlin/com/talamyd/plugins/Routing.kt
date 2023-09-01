package com.talamyd.plugins

import com.talamyd.auth.authRouting
import com.talamyd.course.courseRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    intercept(ApplicationCallPipeline.Fallback) {
        if (call.isHandled) return@intercept
        val status = call.response.status() ?: HttpStatusCode.NotFound
        call.respond(status)
    }
    routing {
        authRouting()
        courseRouting()
    }
}
