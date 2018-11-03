package com.freecanvas.todoapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthUI.IdpConfig;
import com.freecanvas.todoapp.service.AuthUserInfoService

class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN : Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authUserInfoService : AuthUserInfoService = AuthUserInfoService()
        if( authUserInfoService.isLogin()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.login)
    }

    fun getSelectedProviders() : List<AuthUI.IdpConfig> {
        var selectedProviders : ArrayList<AuthUI.IdpConfig> = ArrayList<AuthUI.IdpConfig>()
        selectedProviders.add(IdpConfig.GoogleBuilder().build())
        selectedProviders.add(IdpConfig.PhoneBuilder().build())
        return selectedProviders
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == RC_SIGN_IN) {
            if( data == null){
                showErrorDialog()
                return
            }

            val response : IdpResponse = IdpResponse.fromResultIntent(data)!!
            if( resultCode == RESULT_OK) {
                val user : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                if( user != null){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }else{
                showErrorDialog()
            }
        }else{
            showErrorDialog()
        }
    }

    fun pushLoginButton(view: View) {
        showLoginLayout()
    }

    fun showLoginLayout() {
        val builder : AuthUI.SignInIntentBuilder = AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
                .setAvailableProviders(getSelectedProviders())
                .setIsSmartLockEnabled(false, false);
        startActivityForResult(builder.build(), RC_SIGN_IN);
    }

    fun showErrorDialog() {
        AlertDialog.Builder(this).apply{
            setTitle("ログインエラー")
            setMessage("ログインに失敗しました。")
            setPositiveButton("再認証", DialogInterface.OnClickListener{ _, _->{
                showLoginLayout()
            }})
            setNegativeButton("終了", DialogInterface.OnClickListener{_,_->{
                finishAndRemoveTask()
            }})
        }
    }
}