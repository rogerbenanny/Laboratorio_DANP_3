package com.example.laboratorio4

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.laboratorio4.DataBase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.laboratorio4.Dao.StudentWithCourses
import com.example.laboratorio4.DataBase.CoursesWithStudent

//Clase main
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-database"
        ).fallbackToDestructiveMigration().build()
        //------------------

        val student = database.studentDao()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                student.insertPredefinedData(student)
            }


            val st = withContext(Dispatchers.IO) {
                student.getStudentsWithCourses()
            }

            val st1 = withContext(Dispatchers.IO) {
                student.getCoursesWithStudents()
            }

            withContext(Dispatchers.Main) {
                setContent {

                    MyApp(st,st1)
                }
            }
        }
        //----------------
    }
}
@Composable
fun MyApp(st: List<StudentWithCourses>, st1: List<CoursesWithStudent>) {
    var estado by remember { mutableStateOf(false) }
    if (estado) {
        // Segunda pantalla después de iniciar sesión
        PantallaTablas(st,st1)
    } else {
        // Primera pantalla de inicio de sesión
        PantallaInicio(){ estado = true }
    }

}
//Pantalla Principal
@Composable
fun PantallaInicio(estado: () -> Unit) {
    Column( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                    estado()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Ver Tablas")
        }
    }
}

//Pantalla Tablas
@Composable
fun PantallaTablas(st: List<StudentWithCourses>, st1: List<CoursesWithStudent>) {
    val namesList: List<String> = st.map { studentWithCourses ->
        val studentName = studentWithCourses.student.studentName
        val courseNames = studentWithCourses.courses.map { course -> course.courseName }
        "$studentName - ${courseNames.joinToString(", ")}"
    }

    val coursesWithStudents: List<String> = st1.map { coursesWithStudent ->
        val courseName = coursesWithStudent.course.courseName
        val studentNames = coursesWithStudent.student.map { student -> student.studentName }
        "$courseName - ${studentNames.joinToString(", ")}"
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Cursos - Estudiantes")
        LazyColumn(modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.Black)) {
            items(coursesWithStudents) { name ->
                Text(text = "$name")
                Divider()
            }
        }
        Text(text = "Estudiantes - Cursos")
        LazyColumn(modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.Black)) {
            items(namesList) { name ->
                Text(text = "$name")
                Divider()
            }
        }
    }
}
