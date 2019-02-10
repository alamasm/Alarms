package com.example.egor.alarms.Model.Server.Responses

import Server.Responses.IsRequestToRoomSentResponse
import Server.Responses.ResponseInterface
import Server.Room
import Server.User
import Server.requests.RequestAdapter
import Server.requests.RequestInterface
import com.example.egor.alarms.Model.Server.Alarm
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject

fun main(args: Array<String>) {
    val response = IsRequestToRoomSentResponse(sent = true, roomId = 0)
    val json = JSONObject(gsonForResponse.toJson(response).toString())
    print(json)
}

private val gsonForResponse =
    GsonBuilder().registerTypeAdapter(ResponseInterface::class.java, ResponseAdapter()).create()