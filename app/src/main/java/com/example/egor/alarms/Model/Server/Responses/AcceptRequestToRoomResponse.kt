package com.example.egor.alarms.Model.Server.Responses

import Server.Responses.ResponseInterface
import Server.requests.RequestTypes

data class AcceptRequestToRoomResponse(
    override val responseType: Int = RequestTypes.ACCEPT_REQUEST_TO_ROOM,
    val added: Boolean
) : ResponseInterface