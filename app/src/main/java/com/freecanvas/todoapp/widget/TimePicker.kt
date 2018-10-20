package com.freecanvas.todoapp.widget

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.TimePicker
import java.util.*

interface DateSetListener {
    fun onDateSet(id:Int, year: Int, month: Int, day: Int)
}

class TimePicker() : DialogFragment(), DatePickerDialog.OnDateSetListener{

    var listener : DateSetListener? = null
    var id : Int? = 0

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar : Calendar = Calendar.getInstance()
        val year : Int = calendar.get(Calendar.YEAR)
        val month : Int = calendar.get(Calendar.MONTH)
        val day : Int = calendar.get(Calendar.DATE)
        return DatePickerDialog(context, this, year, month, day)
    }

    fun setId(id:Int){
        this.id = id
    }

    fun setOnListener(listener:DateSetListener) {
        this.listener = listener
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        if( listener!! == null)return
        listener!!.onDateSet(id!!, year, month, day)
    }
}