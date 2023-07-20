package com.talamyd.dao.token

import com.talamyd.model.RefreshTokenFromDB
import com.talamyd.model.TokenPair

interface TokenDao {
    suspend fun insert(params: RefreshTokenFromDB)
    suspend fun getToken(oldRefreshToken: String): RefreshTokenFromDB?
    suspend fun update(newToken: String, oldRefreshToken: String, currentTime: Long)
}