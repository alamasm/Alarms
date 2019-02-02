package Server.requests

import Server.Room

data class ChangeRoomRequest(override val address: String,
                             override val requestType: Int = RequestTypes.CHANGE_ROOM,
                             override val userId: Int,
                             override val userPassword: String,
                             val room: Room
) : RequestInterface