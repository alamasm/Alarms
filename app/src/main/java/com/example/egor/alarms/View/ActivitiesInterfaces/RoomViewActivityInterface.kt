package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room

interface RoomViewActivityInterface : LoadingActivityInterface, ActivityInterface {
    fun setRoom(room: Room)
    fun getRoom(): Room
    fun requestToRoomSent(sent: Boolean)
    fun setAdmin(isAdmin: Boolean)
    fun getPageId(): Int
    fun showAddAlarmDialog()
    fun setAccepted(accepted: Boolean)
}