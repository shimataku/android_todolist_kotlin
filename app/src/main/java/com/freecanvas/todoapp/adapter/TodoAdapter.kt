package com.freecanvas.todoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.freecanvas.todoapp.R
import com.freecanvas.todoapp.databinding.TodoCellBinding
import com.freecanvas.todoapp.entity.Todo
import com.freecanvas.todoapp.shelf.TodoShelf

class TodoAdapter(val parentContext:Context, val todoShelf : TodoShelf) : BaseAdapter() {

    var inflater : LayoutInflater
    val todos = todoShelf

    init {
        inflater = LayoutInflater.from(parentContext)
    }

    override fun getView(position : Int, p1: View?, parent : ViewGroup?): View {
        var binding: TodoCellBinding?
        if( p1 == null) {
            binding = TodoCellBinding.inflate(inflater, parent, false)
            binding.root.tag = binding
        }else{
            binding = p1.tag as TodoCellBinding
        }
        binding?.todo = getItem(position) as Todo
        return binding?.root
    }

    override fun getItem(p0: Int): Any {
        return todos.bookAt(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return todos.count
    }

}