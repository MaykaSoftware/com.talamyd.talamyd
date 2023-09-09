package com.talamyd.course

import com.talamyd.course.model.TalamydCourse

class CourseDaoImpl : CourseDao {
    override fun getCourses(courseLevel: Int): List<TalamydCourse> = dummyListCourses().filter {
        it.id < courseLevel
    }
}

fun dummyListCourses(): List<TalamydCourse> {
    val dummyList = mutableListOf<TalamydCourse>(
        TalamydCourse(1, "Tawheed", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(2, "Quraan", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(3, "Fiqh", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(4, "Hadith", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(5, "Arabic", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(6, "Tafsir", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(7, "Tajweed", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(8, "Akhlaaq", "Learn fiqh the right way", "https://image.nl", "adc123"),
        TalamydCourse(9, "Anasheed", "Learn fiqh the right way", "https://image.nl", "adc123")
    )

    return dummyList
}