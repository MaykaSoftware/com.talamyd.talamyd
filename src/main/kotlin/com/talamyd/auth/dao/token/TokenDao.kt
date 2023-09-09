package com.talamyd.auth.dao.token

import com.talamyd.auth.model.RefreshTokenDB

interface TokenDao {
    suspend fun insert(params: RefreshTokenDB)
    suspend fun getToken(oldRefreshToken: String): RefreshTokenDB?
    suspend fun update(newToken: String, oldRefreshToken: String, currentTime: Long)
}