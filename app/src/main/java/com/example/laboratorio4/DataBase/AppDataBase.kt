package com.example.laboratorio4.DataBase

import androidx.room.*
import com.example.laboratorio4.Dao.CourseDao
import com.example.laboratorio4.Dao.StudentDao
import com.example.laboratorio4.Entidades.Course
import com.example.laboratorio4.Entidades.Student
import com.example.laboratorio4.Entidades.StudentCourseCrossRef

@Database(
    entities = [Student::class, Course::class, StudentCourseCrossRef::class],
    version = 10
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun courseDao(): CourseDao
}

// Definición de relaciones Estudiantes-Cursos
data class StudentWithCourses(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "courseId",
        associateBy = Junction(StudentCourseCrossRef::class)
    )
    val courses: List<Course>
)
// Definición de relaciones Cursos-Estudiantes
data class CoursesWithStudent(
    @Embedded val course : Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "studentId",
        associateBy = Junction(StudentCourseCrossRef::class)
    )
    val student: List<Student>
)