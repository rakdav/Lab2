package com.example.lab2.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lab2.R
import com.example.lab2.databinding.FragmentStudentBinding
import com.example.lab2.model.Student
import com.example.lab2.viewmodel.StudentViewModel
import java.util.UUID
private const val ARG_STUDENT_ID="student_id"
private const val TAG="StudentFragment"
private const val ARG_STUDENT = "student"
private const val REQUEST_STUDENT = 0

class StudentFragment : Fragment(R.layout.fragment_student) {

    companion object {
        fun newInstance(studentId:UUID) :StudentFragment {
            val args=Bundle().apply {
                putSerializable(ARG_STUDENT_ID,studentId)
            }
            return StudentFragment().apply {
                arguments=args
            }
        }
    }
    private lateinit var binding: FragmentStudentBinding
    private lateinit var student:Student
    private val studentViewModel: StudentViewModel by lazy {
        ViewModelProvider(this).get(StudentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        student=Student()
        val studentId:UUID=arguments?.getSerializable(ARG_STUDENT_ID) as UUID
        studentViewModel.loadStudent(studentId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentStudentBinding.inflate(layoutInflater,container,false)
        studentViewModel.studentLiveData.observe(viewLifecycleOwner,
            Observer {
                    student->
                student?.let {
                    this.student=student
                    binding.tvFIO.text=student.name
                    binding.tvGroup.text=student.group
                    binding.tvBirthDate.text=student.birthYear.toString()
                    binding.tvPhis.text=student.phis.toString()
                    binding.tvMath.text=student.math.toString()
                    binding.tvInfo.text=student.inf.toString()
                }
            })
        binding.btEdit.setOnClickListener {
                StudentDialogFragment.newInstance(student).apply {
                    setTargetFragment(this@StudentFragment, REQUEST_STUDENT)
                    show(this@StudentFragment.requireFragmentManager(), ARG_STUDENT)
                }
        }
        return binding.root
    }
}