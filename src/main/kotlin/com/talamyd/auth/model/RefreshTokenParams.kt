package com.talamyd.auth.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object RefreshTokenRow : Table(name = "refresh_tokens") {
    val email = varchar(name = "email", length = 250)
    val refresh_token = varchar(name = "refresh_token", length = 300)
    val expires_at = long(name = "expires_at")

    override val primaryKey: PrimaryKey = PrimaryKey(email)

}

@Serializable
data class RefreshTokenParams(val refreshToken: String)

data class RefreshTokenDB(val email: String, val refreshToken: String, val expiresAt: Long)

@Serializable
data class TokenPairResponse(
    val data: TokenPair? = null,
    val errorMessage: String? = null
)

@Serializable
data class TokenPair(
    val accessToken: String,
    val refreshTokenData: RefreshTokenData
)

@Serializable
data class RefreshTokenData(
    val refreshToken: String,
    val expiresAt: Long
)