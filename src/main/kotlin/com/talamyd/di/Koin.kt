package com.talamyd.di

import com.talamyd.auth.authModule
import com.talamyd.course.courseModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        modules(authModule, courseModule)
    }
}