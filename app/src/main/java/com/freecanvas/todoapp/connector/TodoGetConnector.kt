package com.freecanvas.todoapp.connector

import android.content.res.Resources
import android.provider.Settings.Secure.getString
import android.util.Log
import com.freecanvas.todoapp.R
import com.freecanvas.todoapp.entity.ErrorResponse
import com.freecanvas.todoapp.entity.Todo
import com.freecanvas.todoapp.entity.TodoJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class TodoGetConnector(resource : Resources) {

    val resource : Resources = resource

    fun connect(success:(TodoJson)->Unit,error:(ErrorResponse)->Unit){
        val url = resource.getString(R.string.domain) + "/inputform"
         url.httpGet().response{
            request, response, result->
            when(result) {
                is Result.Success -> {
                    println("非同期処理の結果：" + String(response.data))
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val adapter = moshi.adapter(TodoJson::class.java)
                    val res = adapter.fromJson(String(response.data)) as TodoJson
                    success(res);
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                    val errorResponse : ErrorResponse = ErrorResponse(response.url, response.statusCode, response.responseMessage)
                    error(errorResponse)
                }
            }
        }
   }
   fun connect2(success:(TodoJson)->Unit,error:(ErrorResponse)->Unit){
       // 同期処理
       val url = resource.getString(R.string.domain) + "/inputform"
       val triple = url.httpGet().response()
       // レスポンスボディを表示
       println("同期処理の結果：" + String(triple.second.data))

       val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
       val adapter = moshi.adapter(TodoJson::class.java)
       val res = adapter.fromJson(String(triple.second.data)) as TodoJson
       Log.e("", String(triple.second.data))
       success(res);
   }
}