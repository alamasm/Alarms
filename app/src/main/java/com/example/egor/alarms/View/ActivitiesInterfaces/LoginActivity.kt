package com.example.egor.alarms.View.ActivitiesInterfaces

interface LoginActivity: LoadingActivity {
    fun getUsername(): String
    fun getPassword(): String
    fun onLoginError(error: String)
    fun onLoginOk()
}