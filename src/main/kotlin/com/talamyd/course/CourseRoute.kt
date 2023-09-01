package com.talamyd.course

import com.talamyd.auth.model.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.courseRouting() {
    val repository by inject<CourseRepository>()

    authenticate {
        route(path = "/courses"){
            get {
                val params = call.parameters["level"]

                if (params == null) {
                    call.respond(
                        status = HttpStatusCode.BadRequest, message = AuthResponse(
                            errorMessage = "Error getting courses!"
                        )
                    )
                    return@get
                }

                val result = repository.getCourses(params.toInt())
                call.respond(
                    status = result.code, message = result.data
                )
            }
        }
    }
}