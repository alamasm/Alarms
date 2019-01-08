package com.example.egor.alarms.Model.Server.Responses

import Server.requests.*
import com.google.gson.*
import java.lang.reflect.Type

class RequestAdapter: JsonSerializer<RequestInterface>, JsonDeserializer<RequestInterface> {
    override fun serialize(p0: RequestInterface?, p1: Type?, p2: JsonSerializationContext?): JsonElement {
        return JsonObject()
    }

    override fun deserialize(p0: JsonElement?, p1: Type?, p2: JsonDeserializationContext?): RequestInterface {
        val obj = p0?.asJsonObject
        val requestType = obj?.get("requestType")!!.asInt
        when (requestType) {
            RequestTypes.LOGIN -> return p2!!.deserialize(p0, LoginRequest::class.java)
            RequestTypes.REGISTER -> return p2!!.deserialize(p0, RegisterRequest::class.java)
            RequestTypes.TURN_OFF_ALARM -> return p2!!.deserialize(p0, TurnOffAlarmRequest::class.java)
            RequestTypes.SEND_REQUEST_TO_ROOM -> return p2!!.deserialize(p0, SendRequestToRoomRequest::class.java)
            RequestTypes.SEARCH_ROOM -> return p2!!.deserialize(p0, SearchRoomRequest::class.java)
            RequestTypes.CHANGE_ROOM -> return p2!!.deserialize(p0, ChangeRoomRequest::class.java)
            RequestTypes.CREATE_ROOM -> return p2!!.deserialize(p0, CreateRoomRequest::class.java)
            RequestTypes.GET_ROOMS -> return p2!!.deserialize(p0, GetRoomsRequest::class.java)
        }
        return p2!!.deserialize(p0, RequestInterface::class.java)
    }
}