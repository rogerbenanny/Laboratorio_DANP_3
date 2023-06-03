package com.example.laboratorio4.Entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey val studentId: Long,
    val studentName: String
)

@Entity
data class Course(
    @PrimaryKey val courseId: Long,
    val courseName: String
)

@Entity(primaryKeys = ["studentId", "courseId"])
data class StudentCourseCrossRef(
    val studentId: Long,
    val courseId: Long
)