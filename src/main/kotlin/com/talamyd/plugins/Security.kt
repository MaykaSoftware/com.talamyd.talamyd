package com.talamyd.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.talamyd.auth.model.AuthResponse
import com.talamyd.auth.model.RefreshTokenData
import com.talamyd.auth.model.TokenPair
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import java.time.Duration
import java.util.*

//TODO("create a strong secret and hide it")
private const val CLAIM = "email"

private val jwtAudience = System.getenv("jwt.audience")
private val jwtIssuer = System.getenv("jwt.domain")
private val jwtSecret = System.getenv("jwt.secret")

private val jwtAccessLifetime = System.getenv("jwt.access.lifetime").toLong()
private val jwtRefreshLifetime = System.getenv("jwt.refresh.lifetime").toLong()

fun Long.withOffset(offset: Duration) = this + offset.toMillis()

fun Application.configureSecurity() {

    authentication {
        jwt {
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim(CLAIM).asString() != null) {
                    JWTPrincipal(payload = credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = AuthResponse(
                        errorMessage = "Token is not valid or has expired"
                    )
                )
            }
        }
    }
}

fun generateToken(email: String, isUpdate: Boolean = false): TokenPair {
    val currentTime = System.currentTimeMillis()
    val jwt = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM, email)
        .withExpiresAt(Date(currentTime.withOffset(Duration.ofMinutes(jwtAccessLifetime))))
        .sign(Algorithm.HMAC256(jwtSecret))

    val refreshToken = UUID.randomUUID().toString()
    val refreshData = RefreshTokenData(
        refreshToken,
        currentTime.withOffset(Duration.ofDays(jwtRefreshLifetime))
    )
    return TokenPair(
        jwt,
        refreshData
    )
}


