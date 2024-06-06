package com.example.lab2.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.lab2.StudentRepository
import com.example.lab2.model.Student
import java.util.UUID

class StudentViewModel : ViewModel() {
    private val studentRepository=StudentRepository.get()
    private val studentIdLiveData=MutableLiveData<UUID>()
    var studentLiveData:LiveData<Student?> =
        studentIdLiveData.switchMap { studentId->studentRepository.getStudent(studentId) }
    fun loadStudent(studentID: UUID){
        studentIdLiveData.value=studentID
    }
    fun updateStudent(student: Student){
        studentRepository.updateStudent(student)
    }
    fun saveStudent(student: Student){
        studentRepository.insertStudent(student)
    }
    fun  deleteStudent(student: Student){
        studentRepository.deleteStudent(student)
    }
}