package com.freecanvas.todoapp.connector

import android.content.res.Resources
import com.freecanvas.todoapp.entity.ErrorResponse
import com.freecanvas.todoapp.entity.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*

class UserConnector() {

    companion object {
        const val ERROR_CODE_USER_CONNECTOR_NON_REGISTER = -300
        const val ERROR_CODE_USER_CONNECTOR_MUL_REGISTER = -301
    }

    /**
     * @param user ユーザー情報
     */
    fun connectPost(user:User, success:()->Unit, error:(ErrorResponse)->Unit) {
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        val userConnection : CollectionReference = db.collection("user")
        db.collection("users")
                .add(user.toMap())
                .addOnSuccessListener(OnSuccessListener<DocumentReference>{
                    success()
                })
                .addOnFailureListener(OnFailureListener(){
                    val errorResponse : ErrorResponse = ErrorResponse("connected error", 0, "error")
                })
    }

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
                                        friend_pass = document.getString("frient_pass"),
                                        friend_pass_created = document.getLong("frient_pass_created")
                                )
                                success(user)
                            }
                        }else{
                            if( it.result.count() == 0 ){
                                val errorResponse : ErrorResponse = ErrorResponse( url="", statusCode = ERROR_CODE_USER_CONNECTOR_NON_REGISTER, message="")
                                error(errorResponse)
                            }else{
                                val errorResponse : ErrorResponse = ErrorResponse( url="", statusCode = ERROR_CODE_USER_CONNECTOR_MUL_REGISTER, message="")
                                error(errorResponse)
                            }
                        }
                    }else{
                        val errorResponse : ErrorResponse = ErrorResponse( "connected error", 0, it.exception.toString())
                        error(errorResponse)
                    }
                })
    }
}