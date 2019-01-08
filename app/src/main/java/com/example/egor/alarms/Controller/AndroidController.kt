package com.example.egor.alarms.Controller

import Server.ServerInterface
import com.example.egor.alarms.Model.DB.DBInterface
import com.example.egor.alarms.View.ActivitiesInterfaces.LoginActivity
import com.example.egor.alarms.View.ActivitiesInterfaces.MainActivityInterface
import com.example.pektu.lifecounter.Model.Preferences.Preferences
import kotlinx.coroutines.*;
import kotlinx.coroutines.experimental.launch

class AndroidController(private val db: DBInterface, private val prefs: Preferences, private val server: ServerInterface): ControllerInterface {
    override fun onMainActivityLoaded(mainActivityInterface: MainActivityInterface) {
        if (!prefs.getBoolean(Preferences.LOGGED_TAG)) mainActivityInterface.startLoginActivity()
    }

    override fun onLoginActivityStarted(loginActivity: LoginActivity) {

    }

    override fun onLoginClicked(loginActivity: LoginActivity) {
        val username = loginActivity.getUsername()
        val password = loginActivity.getPassword()
        launch {

        }
    }

    override fun onRegisterClicked(loginActivity: LoginActivity) {

    }

    override fun onMainActivityClose(mainActivityInterface: MainActivityInterface) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMainActivityRoomClicked(mainActivityInterface: MainActivityInterface, roomID: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMainActivitySearchButtonClicked(mainActivityInterface: MainActivityInterface) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMainActivityAddButtonClicked(mainActivityInterface: MainActivityInterface) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}