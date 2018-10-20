package com.freecanvas.todoapp

import com.freecanvas.todoapp.connector.TodoGetConnector
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun connectTest() {
        val connector : TodoGetConnector = TodoGetConnector()
        connector.connect(success = {
            println("success")
        }, error = {
            println("error")
        })
    }

    @Test
    fun connectTest2() {
        val connector : TodoGetConnector = TodoGetConnector()
        connector.connect2(
                success = {
                    println("success")
                }, error = {
            println("error")
        }
        )
    }
}
