package Server.requests

import Server.Room

data class CreateRoomRequest(override val address: String,
                             override val requestType: Int = RequestTypes.CREATE_ROOM,
                             override val userID: Int,
                             override val userPassword: String,
                             val room: Room): RequestInterface