package com.freecanvas.todoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.freecanvas.todoapp.adapter.TodoAdapter
import com.freecanvas.todoapp.connector.TodoGetConnector
import com.freecanvas.todoapp.entity.Data
import com.freecanvas.todoapp.entity.Todo
import com.freecanvas.todoapp.entity.TodoJson
import com.freecanvas.todoapp.shelf.TodoShelf

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val thread = Thread{
            val todoGetConnector : TodoGetConnector = TodoGetConnector(resources)
            todoGetConnector.connect(
                   success = {
                       val todoList = mutableListOf<Todo>()
                       it.data!!.forEach {
                           val todo : Todo = Todo(it!!.title!!, it!!.description!!)
                           todoList.add(todo)
                       }
                       val todoShelf = TodoShelf(todos = todoList)

                       runOnUiThread{
                           var listView : ListView = findViewById(R.id.listView) as ListView
                           listView.adapter = TodoAdapter(this, todoShelf)
                       }
                   }
                    ,error = {
                        println("connected error")
                        val todoList = mutableListOf<Todo>()
                        for(i in 1..100) {
                            val todo : Todo = Todo("sample title", "sample message")
                            todoList.add(todo)
                        }
                        val todoShelf = TodoShelf(todos = todoList)
                        runOnUiThread{
                            val listView : ListView = findViewById(R.id.listView) as ListView
                            listView.adapter = TodoAdapter(this, todoShelf)
                        }
                   }
            )
        }.start()

        val button = findViewById<Button>(R.id.add_button)
        button.setOnClickListener(View.OnClickListener {
            println("push!!")
        })
    }
}
