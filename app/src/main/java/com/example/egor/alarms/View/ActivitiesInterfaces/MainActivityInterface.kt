package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room


interface MainActivityInterface : LoadingActivity, Activity {
    fun startSearchRoomActivity()
    fun startLoginActivity()
    fun startRegisterActivity()
    fun startRoomViewActivity(room: Room? = null, canChange: Boolean = false)
    fun updateRooms(rooms: List<Room>)
}