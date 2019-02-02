package com.example.egor.alarms.Model.Server

import Server.Responses.*
import Server.Room
import Server.ServerInterface
import Server.requests.*
import com.example.egor.alarms.Model.Server.Responses.GetRoomResponse
import com.example.egor.alarms.Model.Server.Responses.ResponseAdapter
import com.example.egor.alarms.Model.Server.requests.GetRoomReqest
import com.google.gson.GsonBuilder
import org.json.JSONObject
import khttp.*
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

class JSonServer: ServerInterface {
    override suspend fun register(username: String, passwordHash: String): RegisterResponse? {
        val registerRequest = RegisterRequest("", RequestTypes.REGISTER, -1, passwordHash, username)
        val responseJson = (sendPost(registerRequest) as RegisterResponse?)
        return responseJson
    }

    override suspend fun login(username: String, passwordHash: String): LoginResponse? {
        val loginRequest = LoginRequest("", RequestTypes.LOGIN, -1, passwordHash, username)
        val responseJson = sendPost(loginRequest) as LoginResponse?
        return responseJson
    }

    override suspend fun createRoom(userId: Int, passwordHash: String, room: Room): CreateRoomResponse? {
        val createRoomRequest = CreateRoomRequest("", userId = userId, userPassword = passwordHash, room = room)
        val responseJson = sendPost(createRoomRequest) as CreateRoomResponse?
        return responseJson
    }

    override suspend fun changeRoom(userId: Int, passwordHash: String, newRoom: Room): ChangeRoomResponse? {
        val changeRoomRequest = ChangeRoomRequest("", userId = userId, userPassword = passwordHash, room = newRoom)
        val responseJson = sendPost(changeRoomRequest) as ChangeRoomResponse?
        return responseJson
    }

    override suspend fun getRooms(userID: Int, passwordHash: String): GetRoomsResponse? {
        val getRoomsRequest = GetRoomsRequest("", userId = userID, userPassword = passwordHash)
        val responseJson = sendPost(getRoomsRequest) as GetRoomsResponse?
        return responseJson
    }

    override suspend fun searchRoom(
        usedID: Int,
        passwordHash: String,
        roomName: String
    ): SearchRoomResponse? {
        val searchRoomRequest = SearchRoomRequest("", userId = usedID, userPassword = passwordHash, roomName = roomName)
        val responseJson = sendPost(searchRoomRequest) as SearchRoomResponse?
        return responseJson
    }

    override suspend fun sendRequestToRoom(
        userId: Int,
        passwordHash: String,
        roomId: Int
    ): SendRequestToRoomResponse? {
        val sendRequestToRoomRequest =
            SendRequestToRoomRequest("", userId = userId, userPassword = passwordHash, roomID = roomId)
        val responseJson = sendPost(sendRequestToRoomRequest) as SendRequestToRoomResponse?
        return responseJson
    }

    override suspend fun allUsersTurnedAlarmOff(
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    ): AllUsersTurnedOffAlarmResponse? {
        val allUsersTurnedOffAlarmRequest =
            AllUsersTurnedOffAlarmRequest(userId = userId, roomID = roomId, address = "", userPassword = passwordHash)
        val responseJson = sendPost(allUsersTurnedOffAlarmRequest) as AllUsersTurnedOffAlarmResponse?
        return responseJson
    }

    override suspend fun turnOffAlarm(
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    ): TurnOffAlarmResponse? {
        val turnOffAlarmRequest = TurnOffAlarmRequest("", userId = userId, userPassword = passwordHash, roomID = roomId)
        val responseJson = sendPost(turnOffAlarmRequest) as TurnOffAlarmResponse?
        return responseJson
    }

    override suspend fun getRoom(userId: Int, passwordHash: String, roomId: Int): GetRoomResponse? {
        val getRoomRequest = GetRoomReqest("", userId = userId, userPassword = passwordHash, roomId = roomId)
        val responseJson = sendPost(getRoomRequest) as GetRoomResponse? ?: return null
        return responseJson
    }

    private fun sendPost(request: RequestInterface): ResponseInterface? {
        val json = JSONObject(gsonForRequest.toJson(request).toString())
        print("request " + json + "\n")
        try {
            val r = post(ServerInterface.URL, json = json).text
            print("response " + r)//TODO
            return gsonForResponse.fromJson(r, ResponseInterface::class.java)
        } catch (e: ConnectException) {
            return null
        } catch (e: SocketTimeoutException) {
            return null
        } catch (e: SocketException) {
            return null
        }

    }

    private val gsonForRequest =
        GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
    private val gsonForResponse =
        GsonBuilder().registerTypeAdapter(ResponseInterface::class.java, ResponseAdapter()).create()
}