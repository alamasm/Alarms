package Server

import Server.Responses.*
import com.example.egor.alarms.Model.Server.Responses.GetRoomResponse

interface ServerInterface {
    companion object {
        const val URL = "http://192.168.1.72:5000/alarm_api"
    }

    suspend fun register(username: String, passwordHash: String): RegisterResponse?
    suspend fun login(username: String, passwordHash: String): LoginResponse?
    suspend fun createRoom(userId: Int, passwordHash: String, room: Room): CreateRoomResponse?
    suspend fun changeRoom(userId: Int, passwordHash: String, newRoom: Room): ChangeRoomResponse?
    suspend fun getRooms(userID: Int, passwordHash: String): GetRoomsResponse?
    suspend fun searchRoom(usedID: Int, passwordHash: String, roomName: String): SearchRoomResponse?
    suspend fun sendRequestToRoom(
        userId: Int,
        passwordHash: String,
        roomId: Int,
        message: String
    ): SendRequestToRoomResponse?

    suspend fun isRequestToRoomSent(userId: Int, passwordHash: String, roomId: Int): IsRequestToRoomSentResponse?
    suspend fun turnOffAlarm(userId: Int, passwordHash: String, roomId: Int, alarmId: Int): TurnOffAlarmResponse?
    suspend fun allUsersTurnedAlarmOff(
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    ): AllUsersTurnedOffAlarmResponse?

    suspend fun getRoom(userId: Int, passwordHash: String, roomId: Int): GetRoomResponse?
}