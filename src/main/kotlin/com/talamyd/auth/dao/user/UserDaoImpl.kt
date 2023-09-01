package com.talamyd.auth.dao.user

import com.talamyd.auth.model.SignUpParams
import com.talamyd.auth.model.User
import com.talamyd.auth.model.UserRow
import com.talamyd.database.DatabaseFactory.dbQuery
import com.talamyd.util.hashPassword
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDaoImpl : UserDao {
    override suspend fun insert(params: SignUpParams): User? {
        return dbQuery {
            val insertStatement = UserRow.insert {
                it[name] = params.name
                it[email] = params.email
                it[password] = hashPassword(params.password)
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun findByEmail(email: String): User? {
        return dbQuery {
            UserRow.select { UserRow.email eq email }
                .map { it.toUser() }
                .singleOrNull()
        }
    }

    private fun rowToUser(row: ResultRow): User {
        return User(
            id = row[UserRow.id],
            name = row[UserRow.name],
            bio = row[UserRow.bio],
            avatar = row[UserRow.avatar],
            password = row[UserRow.password]
        )
    }

    private fun ResultRow.toUser() = User(
        this[UserRow.id],
        this[UserRow.name],
        this[UserRow.bio],
        this[UserRow.avatar],
        this[UserRow.password]
    )
}