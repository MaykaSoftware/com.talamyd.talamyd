package com.talamyd.auth.dao.user

import com.talamyd.auth.model.SignUpParams
import com.talamyd.auth.model.User

interface UserDao {
    suspend fun insert(params: SignUpParams): User?
    suspend fun findByEmail(email: String): User?
}