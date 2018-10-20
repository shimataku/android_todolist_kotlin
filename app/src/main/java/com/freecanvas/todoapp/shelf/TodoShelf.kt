package com.freecanvas.todoapp.shelf

import com.freecanvas.todoapp.entity.Todo

class TodoShelf(private val todos: List<Todo>) {
    val count : Int = todos.count()

    fun bookAt(index: Int) : Todo {
        return todos[index]
    }
}