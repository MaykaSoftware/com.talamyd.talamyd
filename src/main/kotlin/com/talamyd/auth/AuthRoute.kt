package com.talamyd.auth

import com.talamyd.auth.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.authRouting() {
    val repository by inject<UserRepository>()

    route(path = "/signup") {
        post {
            val params = call.receiveNullable<SignUpParams>()

            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )
                return@post
            }

            val result = repository.signUp(params = params)
            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }

    route(path = "/login") {
        post {
            val params = call.receiveNullable<SignInParams>()

            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )
                return@post
            }

            val result = repository.signIn(params = params)
            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }

    route(path = "/refresh") {
        post {
            val params = call.receiveNullable<RefreshTokenParams>()

            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = TokenPairResponse(
                        errorMessage = "Session expired"
                    )
                )
                return@post
            }

            val result = repository.refreshToken(params.refreshToken)
            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }
}