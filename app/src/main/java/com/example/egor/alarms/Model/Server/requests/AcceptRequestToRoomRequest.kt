package com.example.egor.alarms.Model.Server.requests

import Server.requests.RequestInterface
import Server.requests.RequestTypes

data class AcceptRequestToRoomRequest(
    override val requestType: Int = RequestTypes.ACCEPT_REQUEST_TO_ROOM,
    override val address: String,
    override val userId: Int,
    override val userPassword: String,
    val userToAcceptId: Int,
    val roomId: Int
) : RequestInterface