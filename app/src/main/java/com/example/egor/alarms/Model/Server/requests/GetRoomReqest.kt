package com.example.egor.alarms.Model.Server.requests

import Server.requests.RequestInterface
import Server.requests.RequestTypes

data class GetRoomReqest(
    override val address: String,
    override val requestType: Int = RequestTypes.GET_ROOM,
    override val userId: Int,
    override val userPassword: String,
    val roomId: Int
) : RequestInterface