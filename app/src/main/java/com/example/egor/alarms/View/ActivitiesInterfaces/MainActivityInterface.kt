package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room


interface MainActivityInterface: LoadingActivity {
    fun startSearchRoomActivity()
    fun startLoginActivity()
    fun startRegisterActivity()
    fun startRoomViewActivity()
    fun updateRooms(rooms: List<Room>)
}