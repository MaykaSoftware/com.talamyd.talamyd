package com.talamyd.auth

import com.talamyd.auth.model.AuthResponse
import com.talamyd.auth.model.SignInParams
import com.talamyd.auth.model.SignUpParams
import com.talamyd.auth.model.TokenPairResponse
import com.talamyd.util.Response

interface UserRepository {
    suspend fun signUp(params: SignUpParams): Response<AuthResponse>
    suspend fun signIn(params: SignInParams): Response<AuthResponse>
    suspend fun refreshToken(refreshToken: String): Response<TokenPairResponse>
}

