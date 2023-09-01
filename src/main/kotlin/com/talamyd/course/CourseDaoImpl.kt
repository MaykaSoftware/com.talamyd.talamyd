package com.talamyd.course

import com.talamyd.course.model.TalamydCourse

class CourseDaoImpl : CourseDao {
    override fun getCourses(courseLevel: Int): List<TalamydCourse> = dummyListCourses().filter {
        it.id < courseLevel
    }
}

fun dummyListCourses(): List<TalamydCourse> {
    val dummyList = mutableListOf<TalamydCourse>()
    for (i in 1..10) {
        dummyList.add(
            TalamydCourse(
                i,
                "title $i",
                "description $i",
                "image url $i",
                "course code $i"
            )
        )
    }
    return dummyList
}