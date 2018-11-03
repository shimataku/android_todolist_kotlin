package com.freecanvas.todoapp.service

import android.content.Context
import com.freecanvas.todoapp.connector.TodoConnector
import com.freecanvas.todoapp.entity.ErrorResponse
import com.freecanvas.todoapp.entity.TodoArrayJson
import com.freecanvas.todoapp.entity.TodoInput
import com.freecanvas.todoapp.entity.TodoJson
import java.util.*

class TodoService(val context: Context) {

    var todoArrayJson: TodoArrayJson = TodoArrayJson(null,null);

    fun connectCreate(todoInput: TodoInput, success:()->Unit, error:(ErrorResponse)->Unit) {
        val authUserInfoService : AuthUserInfoService = AuthUserInfoService()
        val todoJson : TodoJson = TodoJson(
                id = UUID.randomUUID().toString(),
                user = authUserInfoService.getUserUDI(),
                title = todoInput.title,
                publishedDate = todoInput.publishedDate,
                startDate = todoInput.startDate,
                limitDate = todoInput.limitDate,
                isFix = false
        )
        val todoConnector : TodoConnector = TodoConnector()
        todoConnector.connectPost(todoJson = todoJson, success ={
            //取得内容はここで変更
            success()
        },error = {
            error(it)
        })
    }

    fun connectGetArray(success:(TodoArrayJson)->Unit, error:(ErrorResponse)->Unit) {
        val todoConnector : TodoConnector = TodoConnector()
        todoConnector.connectGetList(success = {
            todoArrayJson = it
            success(it)
        },error = {
            error(it)
        })
    }

    fun connectGet(id: String, success:(TodoJson)->Unit, error:(ErrorResponse)->Unit) {
        val todoConnector : TodoConnector = TodoConnector()
        todoConnector.connectGet(id = id, success = {
           success(it)
        }, error = {
            error(it)
        })
    }

    fun getArray() : TodoArrayJson? {
        if(todoArrayJson.count == null)return null
        return todoArrayJson
    }

    fun get(index:Int) : TodoJson? {
        if(todoArrayJson.count == null)return null
        if(todoArrayJson.data == null)return null
        return todoArrayJson.data!![index]
    }
}