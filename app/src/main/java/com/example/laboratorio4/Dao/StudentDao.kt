package com.example.laboratorio4.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.laboratorio4.DataBase.CoursesWithStudent
import com.example.laboratorio4.Entidades.Course
import com.example.laboratorio4.Entidades.Student
import com.example.laboratorio4.Entidades.StudentCourseCrossRef

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student)

    @Insert
    suspend fun insertCourse(course: Course)

    @Insert
    suspend fun insertStudentCourseCrossRef(crossRef: StudentCourseCrossRef)

    @Transaction
    @Query("SELECT * FROM Student")
    fun getStudentsWithCourses(): List<StudentWithCourses>

    @Transaction
    @Query("SELECT * FROM Course")
    fun getCoursesWithStudents(): List<CoursesWithStudent>

    @Query("SELECT * FROM Student WHERE studentName = :name")
    fun getStudentByName(name: String): Student?

    suspend fun insertPredefinedData(studentDao: StudentDao) {
        // Insertar estudiantes

        val student1=Student(1,"Roger")
        val student2=Student(2,"Rider")
        val student3=Student(3,"Lesly")

        studentDao.insertStudent(student1)
        studentDao.insertStudent(student2)
        studentDao.insertStudent(student3)

        val courses1=Course(1,"matematica")
        val courses2=Course(2,"fisica")
        val courses3=Course(3,"quimica")

        studentDao.insertCourse(courses1)
        studentDao.insertCourse(courses2)
        studentDao.insertCourse(courses3)


        val studentCourse1 = StudentCourseCrossRef(1,1)
        val studentCourse2 = StudentCourseCrossRef(1,2)
        val studentCourse3 = StudentCourseCrossRef(1,3)
        val studentCourse4 = StudentCourseCrossRef(2,2)
        val studentCourse5 = StudentCourseCrossRef(2,3)
        val studentCourse6 = StudentCourseCrossRef(3,1)
        val studentCourse7 = StudentCourseCrossRef(3,2)

        studentDao.insertStudentCourseCrossRef(studentCourse1)
        studentDao.insertStudentCourseCrossRef(studentCourse2)
        studentDao.insertStudentCourseCrossRef(studentCourse3)
        studentDao.insertStudentCourseCrossRef(studentCourse4)
        studentDao.insertStudentCourseCrossRef(studentCourse5)
        studentDao.insertStudentCourseCrossRef(studentCourse6)
        studentDao.insertStudentCourseCrossRef(studentCourse7)

    }
}