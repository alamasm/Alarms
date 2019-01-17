package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room

interface RoomViewActivity : LoadingActivity, Activity {
    fun setRoom(room: Room)
}