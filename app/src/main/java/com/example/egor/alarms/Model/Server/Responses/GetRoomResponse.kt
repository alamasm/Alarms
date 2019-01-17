package com.example.egor.alarms.Model.Server.Responses

import Server.Responses.ResponseInterface
import Server.Room
import Server.requests.RequestTypes

data class GetRoomResponse(
    override val responseType: Int = RequestTypes.GET_ROOM,
    val room: Room
) : ResponseInterface