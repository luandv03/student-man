package vn.edu.hust.studentman

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students) { student, action ->
            when (action) {
                "edit" -> showEditStudentDialog(student)
                "delete" -> showDeleteStudentDialog(student)
            }
        }

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddNewStudentDialog()
        }
    }

    private fun showAddNewStudentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_text_name)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_text_id)

        AlertDialog.Builder(this)
            .setTitle("Add New Student")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameEditText.text.toString()
                val id = idEditText.text.toString()
                val newStudent = StudentModel(name, id)
                students.add(newStudent)
                studentAdapter.notifyItemInserted(students.size - 1)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditStudentDialog(student: StudentModel) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_text_name)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_text_id)

        nameEditText.setText(student.name)
        idEditText.setText(student.id)

        AlertDialog.Builder(this)
            .setTitle("Edit Student")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                student.name = nameEditText.text.toString()
                student.id = idEditText.text.toString()
                studentAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteStudentDialog(student: StudentModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete ${student.name}?")
            .setPositiveButton("Delete") { _, _ ->
                val position = students.indexOf(student)
                students.remove(student)
                studentAdapter.notifyItemRemoved(position)
                Snackbar.make(findViewById(R.id.recycler_view_students), "${student.name} deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        students.add(position, student)
                        studentAdapter.notifyItemInserted(position)
                    }
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}