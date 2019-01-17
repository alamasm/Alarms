package Server

import com.example.egor.alarms.Controller.ControllerInterface

interface ServerInterface {
    companion object {
        const val URL = "http://127.0.0.1:5000/alarm_api"
    }

    suspend fun register(controller: ControllerInterface, username: String, passwordHash: String)
    suspend fun login(controller: ControllerInterface, username: String, passwordHash: String)
    suspend fun createRoom(controller: ControllerInterface, userId: Int, passwordHash: String, room: Room)
    suspend fun changeRoom(controller: ControllerInterface, userId: Int, passwordHash: String, newRoom: Room)
    suspend fun getRooms(controller: ControllerInterface, userID: Int, passwordHash: String)
    suspend fun searchRoom(controller: ControllerInterface, usedID: Int, passwordHash: String, roomName: String)
    suspend fun sendRequestToRoom(controller: ControllerInterface, userId: Int, passwordHash: String, roomId: Int)
    suspend fun turnOffAlarm(
        controller: ControllerInterface,
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    )

    suspend fun allUsersTurnedAlarmOff(
        controller: ControllerInterface,
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    )

    suspend fun getRoom(controller: ControllerInterface, userId: Int, passwordHash: String, roomId: Int)
}