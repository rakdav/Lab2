package com.example.lab2

import android.app.Application

class StudentIntentApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        StudentRepository.initialize(this)
    }
}