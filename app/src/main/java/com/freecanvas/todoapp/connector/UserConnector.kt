package com.freecanvas.todoapp.connector

import android.content.res.Resources
import com.freecanvas.todoapp.entity.ErrorResponse
import com.freecanvas.todoapp.entity.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*

class UserConnector() {
    fun connectGet(uid:String, success:(User)->Unit, error:(ErrorResponse)->Unit) {
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        val userConnection : CollectionReference = db.collection("users")
        val query : Query = userConnection.whereEqualTo("uid", uid)
        query.limit(1)
        query.get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> {
                    if(it.isSuccessful()) {
                        val array = mutableListOf<User>()
                        if( it.result.count() != 0) {
                            for(document in it.result) {
                                val user : User = User(
                                        uid = document.getString("uid"),
                                        name = document.getString("name"),
                                        frient_pass = document.getString("frient_pass"),
                                        frient_pass_created = document.getLong("frient_pass_created")
                                )
                                success(user)
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
}