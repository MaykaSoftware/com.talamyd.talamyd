package com.talamyd.course.model

import kotlinx.serialization.Serializable

data class TalamydCourse(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val courseCode: String
)

@Serializable
data class TalamydCourseResponse(
    val data: List<TalamydCourseResponseData>? = null,
    val errorMessage: String? = null
)

@Serializable
data class TalamydCourseResponseData(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val courseCode: String
)