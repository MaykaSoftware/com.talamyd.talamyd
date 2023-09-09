package com.talamyd.auth

import com.talamyd.auth.dao.token.TokenDao
import com.talamyd.auth.dao.user.UserDao
import com.talamyd.auth.model.*
import com.talamyd.plugins.generateToken
import com.talamyd.util.hashPassword
import com.talamyd.util.Response
import io.ktor.http.*

class UserRepositoryImpl(
    private val userDao: UserDao, private val tokenDao: TokenDao
) : UserRepository {

    override suspend fun signUp(params: SignUpParams): Response<AuthResponse> {
        return if (userAlreadyExists(params.email)) {
            Response.Error(
                code = HttpStatusCode.Conflict, data = AuthResponse(
                    errorMessage = "User already exists"
                )
            )
        } else {
            val insertedUser = userDao.insert(params)

            if (insertedUser == null) {
                Response.Error(
                    code = HttpStatusCode.InternalServerError, data = AuthResponse(
                        errorMessage = "Could not register user, try later!"
                    )
                )
            } else {
                val generatedTokens = generateToken(params.email)
                val refreshTokenObject = RefreshTokenDB(
                    params.email,
                    generatedTokens.refreshTokenData.refreshToken,
                    generatedTokens.refreshTokenData.expiresAt
                )
                tokenDao.insert(refreshTokenObject)
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = insertedUser.id,
                            name = insertedUser.name,
                            bio = insertedUser.bio,
                            tokenPair = generatedTokens
                        )
                    )
                )
            }
        }
    }

    override suspend fun signIn(params: SignInParams): Response<AuthResponse> {
        val user = userDao.findByEmail(params.email)

        return if (user == null) {
            Response.Error(
                code = HttpStatusCode.NotFound, data = AuthResponse(
                    errorMessage = "Invalid credentials, no user with this email!"
                )
            )
        } else {
            val hashedPassword = hashPassword(params.password)

            if (user.password == hashedPassword) {
                val generatedToken = generateToken(params.email)
                val refreshTokenObject = RefreshTokenDB(
                    params.email,
                    generatedToken.refreshTokenData.refreshToken,
                    generatedToken.refreshTokenData.expiresAt
                )
                tokenDao.insert(refreshTokenObject)

                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = user.id, name = user.name, bio = user.bio, tokenPair = generatedToken
                        )
                    )
                )
            } else {
                Response.Error(
                    code = HttpStatusCode.Forbidden, data = AuthResponse(
                        errorMessage = "Invalid credentials, wrong password!"
                    )
                )
            }
        }
    }

    override suspend fun refreshToken(refreshToken: String): Response<TokenPairResponse> {
        val oldTokenData = tokenDao.getToken(refreshToken)

        val currentTime = System.currentTimeMillis()

        return if (oldTokenData != null && oldTokenData.expiresAt > currentTime) {
            val generatedTokens = generateToken(oldTokenData.email, true)
            tokenDao.update(
                generatedTokens.refreshTokenData.refreshToken,
                oldTokenData.refreshToken,
                generatedTokens.refreshTokenData.expiresAt
            )
            Response.Success(
                data = TokenPairResponse(
                    data = generatedTokens
                )
            )
        } else {
            Response.Error(
                code = HttpStatusCode.Unauthorized, data = TokenPairResponse(
                    errorMessage = "Session expired"
                )
            )
        }
    }

    private suspend fun userAlreadyExists(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}