package com.talamyd.course

import com.talamyd.course.model.TalamydCourse

interface CourseDao {
    fun getCourses(courseLevel: Int): List<TalamydCourse>?
}