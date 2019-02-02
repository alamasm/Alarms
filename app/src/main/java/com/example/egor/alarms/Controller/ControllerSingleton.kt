package com.example.egor.alarms.Controller

import android.content.Context
import android.content.SharedPreferences
import com.example.egor.alarms.Model.AndroidModel
import com.example.egor.alarms.Model.Cryptography.AndroidCrypter
import com.example.egor.alarms.Model.DB.AndroidDB
import com.example.egor.alarms.Model.DB.DBHelper
import com.example.egor.alarms.Model.Server.JSonServer
import com.example.egor.alarms.Model.main
import com.example.egor.alarms.View.ActivitiesInterfaces.MainActivityInterface
import com.example.pektu.lifecounter.Model.Preferences.AndroidPreferences

object ControllerSingleton {
    private var inited = false
    lateinit var instance: ControllerInterface

    fun init(context: Context, mainActivity: MainActivityInterface) {
        if (!inited) {
            instance = AndroidController(
                AndroidModel(
                    AndroidDB(DBHelper(context)),
                    AndroidPreferences(context.getSharedPreferences("Prefs", 0)), JSonServer()
                ),
                AndroidCrypter(), mainActivity
            )
            inited = true
        }
    }

    fun inited(): Boolean {
        return inited
    }
}