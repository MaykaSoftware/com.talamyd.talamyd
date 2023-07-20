package com.talamyd.dao.token

import com.talamyd.dao.DatabaseFactory.dbQuery
import com.talamyd.model.*
import dev.forst.exposed.insertOrUpdate
import org.jetbrains.exposed.sql.*

class TokenDaoImpl: TokenDao {
    override suspend fun insert(params: RefreshTokenFromDB) {
        return dbQuery {
            val insertStatement = RefreshTokenRow.insertOrUpdate(RefreshTokenRow.email) {
                it[email] = params.email
                it[refresh_token] = params.refreshToken
                it[expires_at] = params.expiresAt
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToToken(it)
            }
        }
    }

    override suspend fun getToken(oldRefreshToken: String): RefreshTokenFromDB? {
        return dbQuery {
            RefreshTokenRow.select { RefreshTokenRow.refresh_token eq oldRefreshToken }
                .map { rowToToken(it) }
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


    private fun rowToToken(row: ResultRow): RefreshTokenFromDB {
        return RefreshTokenFromDB(
            email = row[RefreshTokenRow.email],
            refreshToken = row[RefreshTokenRow.refresh_token],
            expiresAt = row[RefreshTokenRow.expires_at],
        )
    }

    fun ResultRow.toToken() = RefreshTokenFromDB(
        this[RefreshTokenRow.email],
        this[RefreshTokenRow.refresh_token],
        this[RefreshTokenRow.expires_at],
    )
}