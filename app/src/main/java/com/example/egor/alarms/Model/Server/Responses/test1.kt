package com.example.egor.alarms.Model.Server.Responses

import Server.Room
import Server.User
import Server.requests.RequestAdapter
import Server.requests.RequestInterface
import com.example.egor.alarms.Model.Server.Alarm
import com.google.gson.Gson
import com.google.gson.GsonBuilder

fun main(args: Array<String>) {
    val room = Room(
        id = 1,
        name = "room 1",
        adminId = 1,
        users = emptyList(),
        unapprovedUsers = emptyList<Pair<User, String>>(),
        alarms = emptyList<Alarm>()
    )
    val response = GetRoomResponse(room = room)
    print(gsonForRequest.toJson(response).toString())

}

private val gsonForRequest: Gson by lazy {
    GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
}