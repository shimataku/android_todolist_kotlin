package com.freecanvas.todoapp.service

import android.content.Context
import com.freecanvas.todoapp.connector.TodoConnector
import com.freecanvas.todoapp.entity.ErrorResponse
import com.freecanvas.todoapp.entity.TodoArrayJson
import com.freecanvas.todoapp.entity.TodoJson

class TodoService(val context: Context) {

    fun create(todoJson : TodoJson, success:(TodoJson)->Unit, error:(ErrorResponse)->Unit) {
        val todoConnector : TodoConnector = TodoConnector(context.resources)
        todoConnector.connectPost(todoJson = todoJson, success ={
            //取得内容はここで変更
            success(it)
        },error = {
            error(it)
        })
    }

    fun getArray(success:(TodoArrayJson)->Unit, error:(ErrorResponse)->Unit) {
        val todoConnector : TodoConnector = TodoConnector(context.resources)
        todoConnector.connectGetList(success = {
            success(it)
        },error = {
            error(it)
        })
    }

    fun get(id: String, success:(TodoJson)->Unit, error:(ErrorResponse)->Unit) {
        val todoConnector : TodoConnector = TodoConnector(context.resources)
        todoConnector.connectGet(success = {
           success(it)
        }, error = {
            error(it)
        })
    }

}