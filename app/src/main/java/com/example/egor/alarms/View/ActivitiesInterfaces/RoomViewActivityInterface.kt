package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room
import com.example.egor.alarms.Model.Server.Alarm

interface RoomViewActivityInterface : LoadingActivityInterface, ActivityInterface {
    fun setRoom(room: Room)
    fun getRoom(): Room
    fun requestToRoomSent(sent: Boolean)
    fun setAdmin(isAdmin: Boolean)
    fun setRequest(requestSent: Boolean)
    fun getPageId(): Int
    fun showAddAlarmDialog()
    fun showChangeAlarmDialog(alarm: Alarm)
    fun setAccepted(accepted: Boolean)
    fun updateTabs()
    fun startRequestDialog()
}