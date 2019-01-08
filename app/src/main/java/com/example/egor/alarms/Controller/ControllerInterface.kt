package com.example.egor.alarms.Controller

import Server.Room
import com.example.egor.alarms.View.ActivitiesInterfaces.LoadingActivity
import com.example.egor.alarms.View.ActivitiesInterfaces.LoginActivity
import com.example.egor.alarms.View.ActivitiesInterfaces.MainActivityInterface

interface ControllerInterface {
    fun onMainActivityLoaded(mainActivityInterface: MainActivityInterface)

    fun onLoginActivityStarted(loginActivity: LoginActivity)
    fun onLoginClicked(loginActivity: LoginActivity)
    fun onRegisterClicked(loginActivity: LoginActivity)
    fun onLoginResult(logged: Boolean, text: String)
    fun onRegisterResult(registred: Boolean, text: String)


    fun onMainActivityClose(mainActivityInterface: MainActivityInterface)
    fun onMainActivityRoomClicked(mainActivityInterface: MainActivityInterface, roomID: Int)
    fun onMainActivitySearchButtonClicked(mainActivityInterface: MainActivityInterface)
    fun onMainActivityAddButtonClicked(mainActivityInterface: MainActivityInterface)
}