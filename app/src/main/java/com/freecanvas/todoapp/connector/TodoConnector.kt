package com.freecanvas.todoapp.connector

import android.content.res.Resources
import android.provider.Settings.Secure.getString
import android.util.Log
import com.freecanvas.todoapp.R
import com.freecanvas.todoapp.entity.ErrorResponse
import com.freecanvas.todoapp.entity.Todo
import com.freecanvas.todoapp.entity.TodoArrayJson
import com.freecanvas.todoapp.entity.TodoJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URL

class TodoConnector() {

    fun connectGetList(success:(TodoArrayJson)->Unit, error:(ErrorResponse)->Unit){

        val userId : String = FirebaseAuth.getInstance().uid!!

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        val todosCollection : CollectionReference = db.collection("todos")
        val query : Query = todosCollection.whereEqualTo("user", userId)
        query.limit(20)
        query
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> {
                    if( it.isSuccessful() ){
                        val array = mutableListOf<TodoJson>()
                        for (document in it.result) {
                            val todo : TodoJson = TodoJson(id = document.getString("id"),
                                    user = document.getString("user"),
                                    title = document.getString("title"),
                                    description = document.getString("description"),
                                    publishedDate = document.getLong("publishedDate"),
                                    startDate = document.getLong("startDate"),
                                    limitDate = document.getLong("limitDate"),
                                    isFix = document.getBoolean("isFix"))
                            array.add(todo)
                        }
                        val todoArrayJson : TodoArrayJson = TodoArrayJson(array.size, array)
                        success(todoArrayJson)
                    }else{
                        val errorResponse : ErrorResponse = ErrorResponse( "connected error", 0, it.exception.toString())
                        error(errorResponse)
                    }
                })
    }

    fun connectGet(id:String, success:(TodoJson)->Unit, error:(ErrorResponse)->Unit) {
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        val todosCollection : CollectionReference = db.collection("todos")
        val query : Query = todosCollection.whereEqualTo("id", id)
        query.limit(1)
        query.get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> {
                    if( it.isSuccessful() ){
                        val array = mutableListOf<TodoJson>()
                        if( it.result.count() != 0) {
                            for (document in it.result) {
                                val todo: TodoJson = TodoJson(id = document.getString("id"),
                                        user = document.getString("user"),
                                        title = document.getString("title"),
                                        description = document.getString("description"),
                                        publishedDate = document.getLong("publishedDate"),
                                        startDate = document.getLong("startDate"),
                                        limitDate = document.getLong("limitDate"),
                                        isFix = document.getBoolean("isFix"))
                                success(todo)
                            }
                        }else{
                            val errorResponse : ErrorResponse = ErrorResponse("get resource error", 0, "一つ以上のTodoが取得されました。")
                            error(errorResponse)
                        }
                    }else{
                        val errorResponse : ErrorResponse = ErrorResponse( "connected error", 0, it.exception.toString())
                        error(errorResponse)
                    }
                })
    }

    fun connectPost(todoJson:TodoJson, success:()->Unit, error:(ErrorResponse)->Unit){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("todos")
                .add(todoJson.toMap())
                .addOnSuccessListener(OnSuccessListener<DocumentReference>{
                    success()
                })
                .addOnFailureListener(OnFailureListener() {
                    val errorResponse : ErrorResponse = ErrorResponse("", 0, "")
                    error(errorResponse)
                })
    }
}