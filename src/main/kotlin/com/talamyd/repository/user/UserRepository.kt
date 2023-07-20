package com.talamyd.repository.user

import com.talamyd.model.AuthResponse
import com.talamyd.model.SignInParams
import com.talamyd.model.SignUpParams
import com.talamyd.model.TokenPairResponse
import com.talamyd.util.Response

interface UserRepository {
    suspend fun signUp(params: SignUpParams): Response<AuthResponse>
    suspend fun signIn(params: SignInParams): Response<AuthResponse>
    suspend fun refreshToken(refreshToken: String): Response<TokenPairResponse>
}

