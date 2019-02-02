package com.example.egor.alarms.View.ActivitiesInterfaces
import Server.Room

interface SearchRoomActivityInterface : LoadingActivityInterface, ActivityInterface {
    fun updateRooms(rooms: List<Room>)
    fun startRoomViewActivity(room: Room)
    fun getSearchText(): String
    fun startRoomViewActivity()
}