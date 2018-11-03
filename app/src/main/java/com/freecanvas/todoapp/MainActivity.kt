package com.freecanvas.todoapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.freecanvas.todoapp.adapter.TodoAdapter
import com.freecanvas.todoapp.connector.TodoConnector
import com.freecanvas.todoapp.entity.Todo
import com.freecanvas.todoapp.entity.TodoJson
import com.freecanvas.todoapp.service.TodoService
import com.freecanvas.todoapp.shelf.TodoShelf

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener{

    val todoService : TodoService = TodoService(context=this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        todoService.connectGetArray(success = {
            val todoList = mutableListOf<Todo>()
            it.data!!.forEach {
                val todo : Todo = Todo(it!!.id!!, it!!.title!!)
                todoList.add(todo)
            }
            val todoShelf = TodoShelf(todos = todoList)
            runOnUiThread{
                setListVieAdapter(todoShelf)
            }
        }, error = {
            println("connected error")
            val todoList = mutableListOf<Todo>()
            for(i in 1..100) {
                val todo : Todo = Todo("id", "sample title")
                todoList.add(todo)
            }
            val todoShelf = TodoShelf(todos = todoList)
            runOnUiThread{
                setListVieAdapter(todoShelf)
            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val todoJson = todoService.get(position)
        if(todoJson!=null){

        }
    }


    fun setListVieAdapter(todoShelf : TodoShelf) {
        var listView : ListView = findViewById(R.id.listView) as ListView
        listView.adapter = TodoAdapter(this, todoShelf)
        listView.onItemClickListener = this
    }

    fun pushTodoAddButton(view: View) {
        val intent = Intent(this, AddTodoActivity::class.java)
        startActivity(intent)
    }
}

