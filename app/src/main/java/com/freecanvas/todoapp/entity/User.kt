package com.freecanvas.todoapp.entity

data class User(val uid:String?, val name:String?, val friend_pass:String?, val friend_pass_created:Long?){
    fun toMap() : Map<String, Any?>{
        val map = mapOf(
                "uid" to uid,
                "name" to name,
                "friend_pass" to friend_pass,
                "friend_pass_created" to friend_pass_created
        )
        return map
    }
}