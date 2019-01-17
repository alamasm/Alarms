package com.example.egor.alarms.Model.Server.Responses

import Server.Responses.*
import Server.requests.*
import com.google.gson.*
import java.lang.reflect.Type

class ResponseAdapter : JsonSerializer<RequestInterface>, JsonDeserializer<RequestInterface> {
    override fun serialize(p0: RequestInterface?, p1: Type?, p2: JsonSerializationContext?): JsonElement {
        return JsonObject()
    }

    override fun deserialize(p0: JsonElement?, p1: Type?, p2: JsonDeserializationContext?): RequestInterface {
        val obj = p0?.asJsonObject
        val requestType = obj?.get("requestType")!!.asInt
        when (requestType) {
            RequestTypes.LOGIN -> return p2!!.deserialize(p0, LoginResponse::class.java)
            RequestTypes.REGISTER -> return p2!!.deserialize(p0, RegisterResponse::class.java)
            RequestTypes.TURN_OFF_ALARM -> return p2!!.deserialize(p0, TurnOffAlarmResponse::class.java)
            RequestTypes.SEND_REQUEST_TO_ROOM -> return p2!!.deserialize(p0, SendRequestToRoomResponse::class.java)
            RequestTypes.SEARCH_ROOM -> return p2!!.deserialize(p0, SearchRoomResponse::class.java)
            RequestTypes.CHANGE_ROOM -> return p2!!.deserialize(p0, ChangeRoomResponse::class.java)
            RequestTypes.CREATE_ROOM -> return p2!!.deserialize(p0, CreateRoomResponse::class.java)
            RequestTypes.GET_ROOMS -> return p2!!.deserialize(p0, GetRoomsResponse::class.java)
            RequestTypes.GET_ROOM -> return p2!!.deserialize(p0, GetRoomResponse::class.java)
        }
        return p2!!.deserialize(p0, ResponseInterface::class.java)
    }
}