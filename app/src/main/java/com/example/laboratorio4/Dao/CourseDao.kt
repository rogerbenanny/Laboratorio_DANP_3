package com.example.laboratorio4.Dao

import androidx.room.*
import com.example.laboratorio4.Entidades.Course
import com.example.laboratorio4.Entidades.Student
import com.example.laboratorio4.Entidades.StudentCourseCrossRef

@Dao
interface CourseDao {
    @Query("SELECT * FROM Course")
    fun getAllCourses(): List<Course>
}

// Definición de relaciones
data class StudentWithCourses(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "courseId",
        associateBy = Junction(StudentCourseCrossRef::class)
    )
    val courses: List<Course>
)