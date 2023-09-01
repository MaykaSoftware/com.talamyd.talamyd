package com.talamyd.auth.dao.token

import com.talamyd.auth.model.RefreshTokenDB
import com.talamyd.auth.model.RefreshTokenRow
import com.talamyd.database.DatabaseFactory.dbQuery
import dev.forst.exposed.insertOrUpdate
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class TokenDaoImpl : TokenDao {
    override suspend fun insert(params: RefreshTokenDB) {
        return dbQuery {
            val insertStatement = RefreshTokenRow.insertOrUpdate(RefreshTokenRow.email) {
                it[email] = params.email
                it[refresh_token] = params.refreshToken
                it[expires_at] = params.expiresAt
            }

            insertStatement.resultedValues?.singleOrNull()?.toToken()
        }
    }

    override suspend fun getToken(oldRefreshToken: String): RefreshTokenDB? {
        return dbQuery {
            RefreshTokenRow.select { RefreshTokenRow.refresh_token eq oldRefreshToken }
                .map { it.toToken() }
                .singleOrNull()
        }
    }

    override suspend fun update(newToken: String, oldRefreshToken: String, currentTime: Long) {
        return dbQuery {
            RefreshTokenRow.update({ RefreshTokenRow.refresh_token eq oldRefreshToken }) {
                it[refresh_token] = newToken
                it[expires_at] = currentTime
            }
        }
    }

    private fun ResultRow.toToken() = RefreshTokenDB(
        this[RefreshTokenRow.email],
        this[RefreshTokenRow.refresh_token],
        this[RefreshTokenRow.expires_at],
    )
}