package com.example.egor.alarms.Controller

import Server.Room
import com.example.egor.alarms.View.ActivitiesInterfaces.LoginActivity
import com.example.egor.alarms.View.ActivitiesInterfaces.MainActivityInterface
import com.example.egor.alarms.View.ActivitiesInterfaces.RoomViewActivity
import com.example.egor.alarms.View.ActivitiesInterfaces.SearchRoomActivity

interface ControllerInterface {
    fun onMainActivityLoaded(mainActivityInterface: MainActivityInterface)

    fun onLoginActivityStarted(loginActivity: LoginActivity)
    fun onLoginActivityFinished()
    fun onSearchRoomActivityStarted(searchRoomActivity: SearchRoomActivity)
    fun onSearchRoomActivityFinished()
    fun onRoomViewActivityStarted(roomViewActivity: RoomViewActivity)
    fun onRoomViewActivityFinished()

    fun onLoginClicked(loginActivity: LoginActivity)
    fun onRegisterClicked(loginActivity: LoginActivity)

    fun onMainActivityAddButtonClicked(mainActivityInterface: MainActivityInterface)
    fun onMainActivityClose(mainActivityInterface: MainActivityInterface)
    fun onMainActivityRoomClicked(mainActivityInterface: MainActivityInterface, roomID: Int)
    fun onMainActivitySearchButtonClicked(mainActivityInterface: MainActivityInterface)

    fun onSearchActivityTextChanged(searchRoomActivity: SearchRoomActivity, text: String)

    suspend fun onLoginResult(logged: Boolean, userID: Int, username: String, passwordHash: String)
    suspend fun onRegisterResult(registred: Boolean, userID: Int, username: String, passwordHash: String)
    suspend fun onCreateRoomResult(created: Boolean, roomID: Int)
    suspend fun onChangeRoomResult(changed: Boolean)
    suspend fun onGetRoomsResult(rooms: List<Room>)
    suspend fun onGetRoomResult(room: Room)
    suspend fun onSendRequestToRoomResult(requestSent: Boolean)
    suspend fun onTurnOffAlarmResult(turnedOff: Boolean)
    suspend fun onAllUserTurnedAlarmOffResult(allUsersOffAlarm: Boolean)
    suspend fun onSearchResult(rooms: List<Room>)
}