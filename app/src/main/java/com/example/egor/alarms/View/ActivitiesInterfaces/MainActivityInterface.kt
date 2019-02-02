package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room


interface MainActivityInterface : LoadingActivityInterface, ActivityInterface {
    fun startLoginActivity()
    fun startRegisterActivity()
    fun startRoomViewActivity(canChange: Boolean = false)
    fun updateRooms(rooms: List<Room>)
    fun getSearchText(): String
    fun showCreateRoomDialog()
}