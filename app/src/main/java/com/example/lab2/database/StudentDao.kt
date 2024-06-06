package com.example.lab2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lab2.model.Student
import java.util.UUID

@Dao
interface StudentDao
{
    @Query("SELECT * FROM Student")
    fun getStudents():LiveData<List<Student>>

    @Query("SELECT * FROM Student WHERE id=(:id)")
    fun getStudent(id:UUID):LiveData<Student?>

    @Insert
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

}