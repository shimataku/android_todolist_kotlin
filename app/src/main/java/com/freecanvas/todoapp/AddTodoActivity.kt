package com.freecanvas.todoapp

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.freecanvas.todoapp.widget.DateSetListener
import com.freecanvas.todoapp.widget.TimePicker

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

    fun pushRegisterButton(view: View) {
        AlertDialog.Builder(this).apply {
            setTitle("確認")
            setMessage("記述内容でTodoを追加します。")
            setPositiveButton("OK", DialogInterface.OnClickListener{ _, _ ->
                Toast.makeText(context, "Dialog OK", Toast.LENGTH_LONG).show()
            })
            setNegativeButton("Cancel", null)
            show()
        }
    }
}