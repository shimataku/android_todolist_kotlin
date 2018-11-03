package com.freecanvas.todoapp.service

import com.freecanvas.todoapp.entity.ErrorResponse
import java.nio.ByteBuffer
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


class UserService {

    fun connectGetUser(success:()->Unit, error:(ErrorResponse)->Unit) {
        val authUserInfoService : AuthUserInfoService = AuthUserInfoService()
        val userUID = authUserInfoService.getUserUDI()
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