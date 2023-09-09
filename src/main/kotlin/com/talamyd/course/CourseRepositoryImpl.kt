package com.talamyd.course

import com.talamyd.course.model.TalamydCourseResponse
import com.talamyd.course.model.TalamydCourseResponseData
import com.talamyd.util.Response
import io.ktor.http.*

class CourseRepositoryImpl(
    private val courseDao: CourseDao
) : CourseRepository {
    override fun getCourses(courseLevel: Int): Response<TalamydCourseResponse> {
        val list = courseDao.getCourses(courseLevel)

        return if (list.isNullOrEmpty()) {
            Response.Error(
                code = HttpStatusCode.NotFound, data = TalamydCourseResponse(
                    errorMessage = "Error reading courses from database!"
                )
            )
        } else {
            val newList = list.map {
                TalamydCourseResponseData(
                    it.id,
                    it.title,
                    it.description,
                    it.imageUrl,
                    it.courseCode
                )
            }

            Response.Success(
                data = TalamydCourseResponse(
                    data = newList
                )
            )
        }
    }
}