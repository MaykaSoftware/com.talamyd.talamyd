package com.talamyd.di

import com.talamyd.dao.token.TokenDao
import com.talamyd.dao.token.TokenDaoImpl
import com.talamyd.dao.user.UserDao
import com.talamyd.dao.user.UserDaoImpl
import com.talamyd.repository.user.UserRepository
import com.talamyd.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<UserDao> { UserDaoImpl() }
    single<TokenDao> {TokenDaoImpl()}
}