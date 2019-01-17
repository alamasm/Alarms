package com.example.egor.alarms.View.ActivitiesInterfaces
import Server.Room

interface SearchRoomActivity : LoadingActivity, Activity {
    fun updateRooms(rooms: List<Room>)
    fun startRoomViewActivity(room: Room)
}