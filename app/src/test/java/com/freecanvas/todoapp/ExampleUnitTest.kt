package com.freecanvas.todoapp

import com.freecanvas.todoapp.connector.TodoConnector
import com.freecanvas.todoapp.service.UserService
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
    fun createPass() {
        val us : UserService = UserService()

        //必ず正の数値が出力されるかを確認
        for (i in 1..10000000) {
            val str: String = us.createFrendPass()
            val number : Int = str.toInt()
            assert(number >= 1)
        }
    }
}
