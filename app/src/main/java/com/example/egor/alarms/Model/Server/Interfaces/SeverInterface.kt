package Server

import com.example.egor.alarms.Controller.ControllerInterface

interface ServerInterface {
    companion object {
        const val URL = "http://127.0.0.1:5000/alarm_api"
    }
    fun register(controller: ControllerInterface, username: String, passwordHash: String)
    fun login(controller: ControllerInterface, userId: Int, passwordHash: String)
    fun createRoom(controller: ControllerInterface, userId: Int, passwordHash: String, room: Room)
    fun changeRoom(controller: ControllerInterface, userId: Int, passwordHash: String, newRoom: Room)
    fun getRooms(controller: ControllerInterface, userID: Int, passwordHash: String)
    fun searchRoom(controller: ControllerInterface, usedID: Int, passwordHash: String, roomName: String)
    fun sendRequestToRoom(controller: ControllerInterface, userId: Int, passwordHash: String, roomId: Int)
    fun turnOffAlarm(controller: ControllerInterface, userId: Int, passwordHash: String, roomId: Int, alarmId: Int)
}