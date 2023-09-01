package com.talamyd.auth

import com.talamyd.auth.dao.token.TokenDao
import com.talamyd.auth.dao.token.TokenDaoImpl
import com.talamyd.auth.dao.user.UserDao
import com.talamyd.auth.dao.user.UserDaoImpl
import org.koin.dsl.module

val authModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<UserDao> { UserDaoImpl() }
    single<TokenDao> { TokenDaoImpl() }
}