package com.example.lab2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.lab2.databinding.StudentDialogBinding
import com.example.lab2.model.Student
import com.example.lab2.viewmodel.StudentViewModel

private const val ARG_STUDENT = "student"
class StudentDialogFragment:DialogFragment() {
    companion object {
        fun newInstance(student: Student): StudentDialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_STUDENT, student)
            }

            return StudentDialogFragment().apply {
                arguments = args
            }
        }
    }
    private lateinit var binding: StudentDialogBinding
    private lateinit var student:Student
    private val studentViewModel: StudentViewModel by lazy {
        ViewModelProvider(this).get(StudentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= StudentDialogBinding.inflate(inflater,container,false)
        var studentArgs:Student
        if(arguments!=null) {
            studentArgs = arguments?.getSerializable(ARG_STUDENT) as Student
            studentViewModel.loadStudent(studentArgs.id)
        }
        studentViewModel.studentLiveData.observe(viewLifecycleOwner
        ) { student ->
            student?.let {
                if (arguments != null) {
                    this.student = student
                    binding.etFIO.setText(student.name)
                    binding.etGroup.setText(student.group)
                    binding.etAge.setText(student.birthYear.toString())
                    binding.etMarkInfo.setText(student.inf.toString())
                    binding.etMarkMath.setText(student.math.toString())
                    binding.etMarkPhis.setText(student.phis.toString())
                }
            }
        }
        binding.btOk.setOnClickListener {

            if (arguments != null) {
                student.name = binding.etFIO.text.toString()
                student.group = binding.etGroup.text.toString()
                student.birthYear = binding.etAge.text.toString().toInt()
                student.phis = binding.etMarkPhis.text.toString().toInt()
                student.math = binding.etMarkMath.text.toString().toInt()
                student.inf = binding.etMarkInfo.text.toString().toInt()
                studentViewModel.updateStudent(student)
            }
            else
            {
                this.student= Student()
                student.name = binding.etFIO.text.toString()
                student.group = binding.etGroup.text.toString()
                student.birthYear = binding.etAge.text.toString().toInt()
                student.phis = binding.etMarkPhis.text.toString().toInt()
                student.math = binding.etMarkMath.text.toString().toInt()
                student.inf = binding.etMarkInfo.text.toString().toInt()
                studentViewModel.saveStudent(student)
            }
            dismiss()
        }
        binding.btCancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}