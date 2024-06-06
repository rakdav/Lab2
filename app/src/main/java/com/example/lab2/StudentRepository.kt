package com.example.lab2

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.lab2.database.StudentDatabase
import com.example.lab2.model.Student
import java.util.UUID
import java.util.concurrent.Executors

private const val DATABASE_NAME="student-database"
class StudentRepository private constructor(context:Context)
{
    private val database:StudentDatabase=
        Room.databaseBuilder(
            context.applicationContext,
            StudentDatabase::class.java,
            DATABASE_NAME
        ).build()
    private val studentDao=database.studentDao()
    private val executor=Executors.newSingleThreadExecutor()
    fun getStudents():LiveData<List<Student>> = studentDao.getStudents()
    fun getStudent(id:UUID):LiveData<Student?> = studentDao.getStudent(id)
    fun insertStudent(student:Student){
        executor.execute {
            studentDao.insertStudent(student)
        }
    }
    fun updateStudent(student: Student){
        executor.execute{
            studentDao.updateStudent(student)
        }
    }
    fun deleteStudent(student: Student){
        executor.execute{
            studentDao.deleteStudent(student)
        }
    }
    companion object {
        private var INSTANCE: StudentRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = StudentRepository(context)
            }
        }

        fun get(): StudentRepository {
            return INSTANCE ?: throw IllegalStateException("StudentRepository must be init")
        }
    }
}