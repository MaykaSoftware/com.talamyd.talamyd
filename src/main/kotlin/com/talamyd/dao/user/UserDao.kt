package com.talamyd.dao.user

import com.talamyd.model.SignUpParams
import com.talamyd.model.User

interface UserDao {
    suspend fun insert(params: SignUpParams): User?
    suspend fun findByEmail(email: String): User?
}