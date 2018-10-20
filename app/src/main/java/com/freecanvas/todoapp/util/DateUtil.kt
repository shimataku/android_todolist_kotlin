package com.freecanvas.todoapp.util

import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//https://qiita.com/emboss369/items/5a3ddea301cbf79d971a
/*
こちらのDateUtilのソースを拝借
 */

fun String.toDate(pattern: String = "yyyy年MM月dd日") : Date? {
    val sdFormat = try {
        SimpleDateFormat(pattern)
    }catch(e: IllegalArgumentException) {
        null
    }
    val date = sdFormat?.let {
        try {
            it.parse(this)
        }catch(e: ParseException) {
            null
        }
    }
    return date
}

fun String.toDateLong(pattern: String = "yyyy年MM月dd日") : Long? {
    val sdFormat = try {
        SimpleDateFormat(pattern)
    }catch(e: IllegalArgumentException) {
        null
    }
    val date = sdFormat?.let {
        try {
            it.parse(this.toString())
        }catch(e: ParseException) {
            null
        }
    }
    return date!!.time
}