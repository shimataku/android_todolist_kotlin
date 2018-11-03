package com.freecanvas.todoapp.entity

import java.net.URL

data class ErrorResponse(val url: String, val statusCode:Int, val message:String)
