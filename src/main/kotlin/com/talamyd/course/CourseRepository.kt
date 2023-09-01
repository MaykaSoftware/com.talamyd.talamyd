package com.talamyd.course

import com.talamyd.course.model.TalamydCourseResponse
import com.talamyd.util.Response

interface CourseRepository {
    fun getCourses(courseLevel: Int): Response<TalamydCourseResponse>
}