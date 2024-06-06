package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lab2.databinding.ActivityMainBinding
import com.example.lab2.model.Student
import com.example.lab2.view.StudentFragment
import com.example.lab2.view.StudentListFragment
import java.util.UUID

private const val TAG="MainActivity"
class MainActivity : AppCompatActivity(),StudentListFragment.Callbacks {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment=supportFragmentManager.
                                findFragmentById(R.id.fragment_container)
        if(currentFragment==null){
            val fragment=StudentListFragment.newInstance()
            supportFragmentManager.beginTransaction().
            add(R.id.fragment_container,fragment).commit()
        }
    }

    override fun onStudentSelected(studentId: UUID) {
        val fragment=StudentFragment.newInstance(studentId)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    fragment).addToBackStack(null).commit()
    }
}