package com.example.egor.alarms.Model

import Server.Responses.ResponseInterface
import Server.Room
import Server.SimpleTime
import Server.User
import Server.requests.RequestAdapter
import Server.requests.RequestInterface
import com.example.egor.alarms.Model.Server.Alarm
import com.example.egor.alarms.Model.Server.Responses.GetRoomResponse
import com.example.egor.alarms.Model.Server.Responses.ResponseAdapter
import com.example.egor.alarms.Model.Server.requests.GetRoomReqest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.FileWriter

fun main(args: Array<String>) {
    val getRoomRequest = GetRoomReqest(address = "", userId = 1, userPassword = "abc", roomId = 1)
    val getRoomFile = FileWriter("getRoomRequest.json")
    val user = User("unapproved user", 0)
    val user2 = User("user1", 1)
    val alarm = Alarm(0, "alarm1", arrayOf(10, 10), "1100101")
    val room = Room(0, "room1", 1, listOf(Pair(user, "hello")), alarms = listOf(alarm), users = listOf(user2))
    gsonForRequest.toJson(getRoomRequest, getRoomFile)
    val getRoomResponse = GetRoomResponse(room = room)
    val getRoomRFile = FileWriter("getRoomResponse.json")
    gsonForResponse.toJson(getRoomResponse, getRoomRFile)
    getRoomFile.close()
    getRoomRFile.close()
}


private val gsonForRequest: Gson by lazy {
    GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
}
private val gsonForResponse: Gson by lazy {
    GsonBuilder().registerTypeAdapter(ResponseInterface::class.java, ResponseAdapter()).create()
}