package com.freecanvas.todoapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.freecanvas.todoapp.entity.User
import com.freecanvas.todoapp.service.AuthUserInfoService
import com.freecanvas.todoapp.service.UserService

class UserInfoEditActivity : AppCompatActivity(){

    var isUpdate : Boolean = false

    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.user_info_edit)

        if( intent.getBooleanExtra("isUpdate", false) == true) {
            isUpdate = true
        }
    }

    fun pushRegisterButton(view: View) {
        if(isUpdate) {
            updateUserInfo()
        }else{
            registerUserInfo()
        }
    }

    fun getEditUserName() : String {
       return findViewById<EditText>(R.id.userNameEdit).text.toString()
    }

    fun registerUserInfo() {

        val authUserService : AuthUserInfoService = AuthUserInfoService()
        val user : User = User(
                uid = authUserService.getUserUDI(),
                name = getEditUserName(),
                friend_pass = null,
                friend_pass_created = null
        )

        val userService : UserService = UserService()
        userService.connectPostUser(user, success = {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, error = {
            if( it.statusCode == UserService.ERROR_CODE_USER_SERVICE_EMPTY_NAME ) {
                showErrorEmptyName()
            }
        })
    }

    fun showErrorEmptyName() {
        AlertDialog.Builder(this).apply {
            setTitle("エラー")
            setMessage("表示名を入力してください。")
            setPositiveButton("OK", null)
            show()
        }
    }

    fun updateUserInfo() {

    }
}