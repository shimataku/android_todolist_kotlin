package com.freecanvas.todoapp.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthUserInfoService {

    fun isLogin() : Boolean {
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        if( auth.currentUser != null)return true
        return false
    }

    fun getUserUDI() : String {
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        if( auth.currentUser == null)return ""
        val currentUser : FirebaseUser = auth.currentUser!!
        return currentUser.uid
    }
}