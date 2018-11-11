package com.freecanvas.todoapp.service

import com.freecanvas.todoapp.connector.UserConnector
import com.freecanvas.todoapp.entity.ErrorResponse
import com.freecanvas.todoapp.entity.User
import java.nio.ByteBuffer
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


class UserService {

    companion object {
        const val ERROR_CODE_USER_SERVICE_EMPTY_NAME = -500
    }

    fun connectGetUser(success:()->Unit, error:(ErrorResponse)->Unit) {
        val authUserInfoService : AuthUserInfoService = AuthUserInfoService()
        val userUID = authUserInfoService.getUserUDI()
        val userConnector : UserConnector = UserConnector()
        userConnector.connectGet(userUID, success = {
            success()
        }, error = {
            error(it)
        })
    }

    /*
    * user.nameが空文字だったらエラー
     */
    fun connectPostUser(user: User, success:()->Unit, error:(ErrorResponse)->Unit) {

        if( user.name!!.length == 0){
            val errorResponse = ErrorResponse(url="", statusCode = ERROR_CODE_USER_SERVICE_EMPTY_NAME, message = "")
            error(errorResponse)
            return
        }

        val authUserInfoService: AuthUserInfoService = AuthUserInfoService()
        val userConnector : UserConnector = UserConnector()
        userConnector.connectPost(user, success ={
            success()
        }, error = {
            error(it)
        })
    }

    fun createFrendPass() : String {
        val pass = createPass(4)
        return pass
    }

    fun createPass(length:Int) : String{
        if(length<=1)return ""
        try {
            val random : SecureRandom = SecureRandom.getInstance("SHA1PRNG")
            var token : ByteArray = ByteArray(length,{0})
            var tokenNumber : Int = 0
            while(true){
                random.nextBytes(token)
                token[0] = 0
                tokenNumber = ByteBuffer.wrap(token).getInt()
                if(tokenNumber>=1)break
            }
            return String.format("%08d",tokenNumber)
        } catch (e: Exception) {
            return ""
        }
    }
}