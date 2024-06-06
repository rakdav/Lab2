package com.example.lab2.view

import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2.R
import com.example.lab2.StudentRepository
import com.example.lab2.databinding.FragmentStudentListBinding
import com.example.lab2.model.Student
import com.example.lab2.viewmodel.StudentListViewModel
import com.example.lab2.viewmodel.StudentViewModel
import java.util.UUID

private const val TAG="StudentListFragment"
private const val DIALOG="StudentDialog"

class StudentListFragment:Fragment(R.layout.fragment_student_list) {
    interface Callbacks{
        fun onStudentSelected(studentId:UUID)
    }
    private var callbacks:Callbacks?=null
    private lateinit var binding: FragmentStudentListBinding
    private var adapter: StudentAdapter?=StudentAdapter(emptyList())
    private val studentListViewModel:StudentListViewModel by lazy {
        ViewModelProvider(this)[StudentListViewModel::class.java]
    }
    private val studentViewModel: StudentViewModel by lazy {
        ViewModelProvider(this).get(StudentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentStudentListBinding.inflate(layoutInflater,
            container,false)
        binding.studentRecyclerView.layoutManager=LinearLayoutManager(context)
        //binding.studentRecyclerView.layoutManager=GridLayoutManager(context,2)
        studentListViewModel.studentListLiveData.observe(viewLifecycleOwner,
            Observer { students->
                adapter=StudentAdapter(students)
                binding.studentRecyclerView.adapter=adapter
                ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT)
                {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val deleteStudent= students[viewHolder.adapterPosition]
                        Log.d("delete",deleteStudent.name)
                        studentViewModel.deleteStudent(deleteStudent)
                        adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }).attachToRecyclerView(binding.studentRecyclerView)
            })
        binding.idAdd.setOnClickListener{
            StudentDialogFragment().apply {
                show(this@StudentListFragment.parentFragmentManager, DIALOG)
            }
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }

    private inner class StudentAdapter(var students:List<Student>):
                RecyclerView.Adapter<StudentAdapter.StudentHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
            val view=layoutInflater.inflate(R.layout.list_item_student,parent,false)
            return StudentHolder(view)
        }

        override fun getItemCount(): Int = students.size

        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            val student=students[position]
            holder.bind(student)
        }
        private inner class StudentHolder(view:View):RecyclerView.ViewHolder(view),
        View.OnClickListener{
            private lateinit var student:Student
            val studentNameTextView:TextView=itemView.findViewById(R.id.student_name)
            val studentGroupTextView:TextView=itemView.findViewById(R.id.student_group)
            val studentYear:TextView=itemView.findViewById(R.id.student_year)
            init {
                itemView.setOnClickListener(this)
            }
            fun bind(student: Student){
                this.student=student
                studentNameTextView.text=student.name
                studentGroupTextView.text=student.group
                studentYear.text=student.birthYear.toString()
            }

            override fun onClick(v: View?) {
                callbacks?.onStudentSelected(student.id)
            }
        }

    }
    companion object{
        fun newInstance():StudentListFragment{
            return StudentListFragment()
        }
    }
}