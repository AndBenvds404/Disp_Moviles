package com.example.myapplication2.logic.validator

import com.example.myapplication2.data.entities.LoginUser

class LoginValidator {

    fun checklogin(name:String, password:String):Boolean{

        val admin = LoginUser()
        return (admin.name ==name && admin.pass == password)

    }
}