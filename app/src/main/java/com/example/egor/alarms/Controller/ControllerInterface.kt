package com.example.egor.alarms.Controller

import com.example.egor.alarms.Model.Server.Alarm
import com.example.egor.alarms.View.ActivitiesInterfaces.*

interface ControllerInterface {
    fun onMainActivityStarted(mainActivity: MainActivityInterface)
    fun onMainActivityRoomButtonClicked(roomID: Int)
    fun onMainActivityFinished()
    fun onMainActivitySearchTextChanged()
    fun onMainActivitySearchClosed()
    fun onMainActivityCreateRoomButtonClicked()
    fun onMainActivityCreateRoomOKButtonClicked(roomName: String)
    fun onMainActivityLogoutClicked()

    fun onRoomViewActivityStarted(roomViewActivity: RoomViewActivityInterface)
    fun onRoomViewActivitySaveRoomClicked(roomViewActivity: RoomViewActivityInterface)
    fun onRoomViewActivitySendRequestClicked(roomViewActivity: RoomViewActivityInterface)
    fun onRoomViewActivitySendRequestOKClicked(roomViewActivity: RoomViewActivityInterface, message: String)
    fun onRoomViewFinished()
    fun onRoomViewActivityAddAlarmButtonClicked(roomViewActivity: RoomViewActivityInterface)
    fun onRoomViewAddAlarmOKClicked(roomViewActivity: RoomViewActivityInterface, alarm: Alarm)
    fun onRoomViewChangeAlarmOKClicked(alarm: Alarm)
    fun onRoomViewRemoveAlarmClicked(alarm: Alarm)
    fun onRoomViewChangeAlarmClicked(alarm: Alarm)

    fun onLoginActivityStarted(loginActivity: LoginActivityInterface)
    fun onLoginActivityLoginClicked(loginActivity: LoginActivityInterface)
    fun onLoginActivityRegisterClicked(loginActivity: LoginActivityInterface)
    fun onLoginActivityFinished()

    fun onInternetError()

    fun updateUsers(usersFragmentInterface: UsersFragmentInterface)
}
