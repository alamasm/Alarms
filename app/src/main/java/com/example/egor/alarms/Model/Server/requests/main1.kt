package Server.requests

import Server.*
import com.example.egor.alarms.Model.Server.Alarm
import java.io.FileWriter
import com.google.gson.*
import khttp.post
import org.json.JSONObject

/*
fun main(args: Array<String>) {
    val changeRoomFile = FileWriter("changeRoomRequest.json")
    val createRoomFile = FileWriter("createRoomRequest.json")
    val getRoomsFile = FileWriter("getRoomsRequest.json")
    val loginFile = FileWriter("loginRequest.json")
    val registerFile = FileWriter("registerRequest.json")
    val searchRoomFile = FileWriter("searchRoomRequest.json")
    val sendRequestToRoomFile = FileWriter("sendRequestToRoomRequest.json")
    val turnOffAlarmFile = FileWriter("turnOffAlarmRequest.json")
    val isAllUsersTurnedOffAlarmFile = FileWriter("isAllUsersTurnedOffAlarmRequest.json")
    val isRequestToRoomAcceptedFile = FileWriter("isRequestToRoomAcceptedRequest.json")

    val user0 = User("user0", 0)
    val user1 = User("user1", 1)
    //val alarm0 = Alarm(0, "alarm0", SimpleTime(10, 10), arrayOf(0, 1, 2))
    //val alarm1 = Alarm(1, "alarm1", SimpleTime(11, 11), arrayOf(5, 6))
        //val room1 = Room(0, "room1",1, listOf(Pair(user0, "add me")), listOf(user0, user1), listOf(alarm0, alarm1), true)
    val changeRoomRequest = ChangeRoomRequest(address = "8.8.8.8", userId = 0, userPassword = "abc", room = room1)
    val createRoomRequest = CreateRoomRequest(address = "8.8.8.8", userId = 0, userPassword = "abc", room = room1)
    val getRoomRequest = GetRoomsRequest(address = "8.8.8.8", userPassword = "abc", userId = 0)
    val loginRoomsRequest = LoginRequest(address = "8.8.8.8", userPassword = "abc", userName = "user1")
    val registerRequest = RegisterRequest(address =  "8.8.8.8", userPassword = "abc", userName = "user1")
    val searchRoomRequest = SearchRoomRequest(address = "8.8.8.8", userId = 0, userPassword = "abc", roomName = "room1")
    val sendRequstToRoomRequest = SendRequestToRoomRequest(address = "8.8.8.8", userId = 0, userPassword = "abc", roomId = 0)
    val turnOffAlarmRequest = TurnOffAlarmRequest(address = "8.8.8.8", userId = 0, userPassword = "abc", roomId = 0)
    val isAllUsersTurnedOffAlarmRequest = AllUsersTurnedOffAlarmRequest(userId=  0, userPassword = "abc", roomId = 0, address = "8.8.8.8")
    val isRequestToRoomAcceptedRequest = IsRequestToRoomAcceptedRequest(userId = 0, userPassword = "abc", address = "8.8.8.8", roomId = 0)
    gsonForRequest.toJson(changeRoomRequest, changeRoomFile)
    gsonForRequest.toJson(createRoomRequest, createRoomFile)
    gsonForRequest.toJson(getRoomRequest, getRoomsFile)
    gsonForRequest.toJson(loginRoomsRequest, loginFile)
    gsonForRequest.toJson(registerRequest, registerFile)
    gsonForRequest.toJson(searchRoomRequest, searchRoomFile)
    gsonForRequest.toJson(sendRequstToRoomRequest, sendRequestToRoomFile)
    gsonForRequest.toJson(turnOffAlarmRequest, turnOffAlarmFile)
    gsonForRequest.toJson(isAllUsersTurnedOffAlarmRequest, isAllUsersTurnedOffAlarmFile)
    gsonForRequest.toJson(isRequestToRoomAcceptedRequest, isRequestToRoomAcceptedFile)
    val req = gsonForRequest.toJson(registerRequest).toString()
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
    print(sendGet(JSONObject(req)))

}

private fun sendGet(requestJSON: JSONObject): String {
    val res = post("http://127.0.0.1:5000/alarm_api", json = requestJSON)
    return res.text
}

private val gsonForRequest: Gson by lazy {
    GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
} */