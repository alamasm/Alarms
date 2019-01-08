package Server.Responses

import com.example.egor.alarms.Model.Server.Alarm
import Server.Room
import Server.SimpleTime
import Server.User
import Server.requests.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.FileWriter

fun main(args: Array<String>) {
    val changeRoomFile = FileWriter("changeRoomResponse.json")
    val createRoomFile = FileWriter("createRoomResponse.json")
    val getRoomsFile = FileWriter("getRoomsResponse.json")
    val loginFile = FileWriter("loginResponse.json")
    val registerFile = FileWriter("registerResponse.json")
    val searchRoomFile = FileWriter("searchRoomResponse.json")
    val sendRequestToRoomFile = FileWriter("sendRequestToRoomResponse.json")
    val turnOffAlarmFile = FileWriter("turnOffAlarmResponse.json")
    val isAllUsersTurnedOffAlarmFile = FileWriter("isAllUsersTurnedOffAlarmResponse.json")
    val isRequestToRoomAcceptedFile = FileWriter("isRequestToRoomAcceptedResponse.json")

    val user0 = User("user0", 0)
    val user1 = User("user1", 1)
    val alarm0 = Alarm(0, "alarm0", SimpleTime(10, 10), arrayOf(0, 1, 2))
    val alarm1 = Alarm(1, "alarm1", SimpleTime(11, 11), arrayOf(5, 6))
    val room1 = Room(0, "room1",1, listOf(Pair(user0, "add me")), listOf(user0, user1), listOf(alarm0, alarm1), true)

    val changeRoomResponse = ChangeRoomResponse(roomChanged = true)
    val createRoomResponse = CreateRoomResponse(roomID = 0, roomCreated = true)
    val getRoomsResponse = GetRoomsResponse(rooms = listOf( room1))
    val loginResponse = LoginResponse(logged = true, userID = 0)
    val registerResponse = RegisterResponse(userID = 0, registred = true)
    val searchRoomResponse = SearchRoomResponse(rooms = listOf(room1))
    val sendRequestToRoomResponse = SendRequestToRoomResponse(requestSent = true)
    val turnOffAlarmResponse = TurnOffAlarmResponse(turnedOff = true)
    val isAllUsersTurnedOffAlarmResponse = AllUsersTurnedOffAlarmResponse(allUsersTurnedOff = true, roomID = 1)
    val isRequestToRoomAcceptedResponse = IsRequestToRoomAcceptedResponse(accepted = true, roomID = 1)
    gsonForRequest.toJson(changeRoomResponse, changeRoomFile)
    gsonForRequest.toJson(createRoomResponse, createRoomFile)
    gsonForRequest.toJson(getRoomsResponse, getRoomsFile)
    gsonForRequest.toJson(loginResponse, loginFile)
    gsonForRequest.toJson(registerResponse, registerFile)
    gsonForRequest.toJson(searchRoomResponse, searchRoomFile)
    gsonForRequest.toJson(sendRequestToRoomResponse, sendRequestToRoomFile)
    gsonForRequest.toJson(turnOffAlarmResponse, turnOffAlarmFile)
    gsonForRequest.toJson(isAllUsersTurnedOffAlarmResponse, isAllUsersTurnedOffAlarmFile)
    gsonForRequest.toJson(isRequestToRoomAcceptedResponse, isRequestToRoomAcceptedFile)
    changeRoomFile.close()
    createRoomFile.close()
    getRoomsFile.close()
    loginFile.close()
    registerFile.close()
    searchRoomFile.close()
    sendRequestToRoomFile.close()
    turnOffAlarmFile.close()
    isAllUsersTurnedOffAlarmFile.close()
    isRequestToRoomAcceptedFile.close()
}

private val gsonForRequest: Gson by lazy {
    GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
}