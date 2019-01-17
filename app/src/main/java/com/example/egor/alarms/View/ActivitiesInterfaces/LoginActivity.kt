package com.example.egor.alarms.View.ActivitiesInterfaces

interface LoginActivity : LoadingActivity, Activity {
    fun getUsername(): String
    fun getPassword(): String
    fun onLoginError(error: String)
    fun onLoginOk()
    fun onRegisterOK()
    fun onRegisterError(error: String)
}