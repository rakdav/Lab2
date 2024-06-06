package com.example.lab2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab2.model.Student

@Database(entities = [Student::class], version = 2)
@TypeConverters(StudentTypeConvertor::class)
abstract class StudentDatabase:RoomDatabase() {
    abstract fun studentDao():StudentDao
}