package com.talamyd.course

import org.koin.dsl.module

val courseModule = module {
    single<CourseDao> { CourseDaoImpl() }
    single<CourseRepository> { CourseRepositoryImpl(get()) }
}