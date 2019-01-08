package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room

interface RoomViewActivity: LoadingActivity {
    fun setRoom(room: Room)
}