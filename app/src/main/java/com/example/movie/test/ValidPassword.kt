package com.example.movie.test

class ValidPassword {
    fun isPasswordValid(pass: String): String{
        return if (pass.length<6){
            "Password Should be More than 6 characters"
        }else if (pass.length>12){
            "Password Shouldn't be More than 6 characters"
        }else{
            "Valid Password"
        }
    }
    fun reverseString(input:String): String{
        return input.reversed()
    }
}