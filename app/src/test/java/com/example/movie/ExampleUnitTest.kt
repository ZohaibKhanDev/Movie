package com.example.movie

import com.example.movie.test.ValidPassword
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun isPasswordValid_less_expectedTrue(){
        //Arrange
        val validPassword = ValidPassword()
        //Act
        val result = validPassword.isPasswordValid("123")
        //Assert
        assertEquals("Password Should be More than 6 characters",result)
    }

    @Test
    fun isReverseValid_less_expectedTrue(){
        //Arrange
        val validPassword = ValidPassword()
        //Act
        val result = validPassword.reverseString("Hello")
        //Assert
        assertEquals("olleH",result)
    }


}