package com.freecanvas.todoapp

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.freecanvas.todoapp.connector.TodoConnector
import com.freecanvas.todoapp.entity.TodoInput
import com.freecanvas.todoapp.entity.TodoJson
import com.freecanvas.todoapp.service.TodoService
import com.freecanvas.todoapp.util.toDateLong
import com.freecanvas.todoapp.widget.DateSetListener
import com.freecanvas.todoapp.widget.TimePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.*

class AddTodoActivity : AppCompatActivity(), DateSetListener {

    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.new_todo_inputform)
    }

    override fun onDateSet(id:Int, year:Int, month:Int, day:Int) {
        val settingDate = String.format("%4d年%2d月%2d日", year, month, day)
        val textEdit = findViewById<EditText>(id)
        textEdit.setText(settingDate)
    }

    fun showStartDatePicker(view: View) {
        showDatePickerDialog(R.id.start_date_edit)
    }

    fun showEndDatePicker(view: View) {
        showDatePickerDialog(R.id.end_date_edit)
    }

    fun showDatePickerDialog(id: Int) {
        val newFragment: TimePicker = TimePicker()
        newFragment.setOnListener(this)
        newFragment.setId(id)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun showConfirmDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("確認")
            setMessage("記述内容でTodoを追加します。")
            setPositiveButton("OK", DialogInterface.OnClickListener{ _, _ ->
                val title : String = findViewById<EditText>(R.id.title_edit).text.toString()
                val startDate : Long = findViewById<EditText>(R.id.start_date_edit).text.toString().toDateLong()!!
                val endDate : Long = findViewById<EditText>(R.id.end_date_edit).text.toString().toDateLong()!!
                val todoInput : TodoInput = TodoInput(
                        title = title,
                        publishedDate = Date().time,
                        startDate = startDate,
                        limitDate = endDate,
                        isFix = false
                )
                val todoService : TodoService = TodoService(context)
                todoService.connectCreate(todoInput, success = {
                    runOnUiThread{
                        showCompDialog()
                    }
                }, error = {
                    AlertDialog.Builder(this.context).apply {
                        setTitle(String.format("%d", it.statusCode))
                        setMessage(it.message)
                    }
                })

            })
            setNegativeButton("Cancel", null)
            show()
        }
    }

    fun showCompDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("登録しました。")
            setPositiveButton("OK", DialogInterface.OnClickListener{ _, _ ->
                finishThisActivity()
            })
            show()
        }
    }

    fun pushRegisterButton(view: View) {
        showConfirmDialog()
    }

    fun finishThisActivity() {
        finish()
    }
}