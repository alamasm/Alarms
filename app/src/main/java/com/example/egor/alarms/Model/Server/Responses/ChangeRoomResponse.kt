package Server.Responses

import Server.requests.RequestTypes

data class ChangeRoomResponse(override val responseType: Int = RequestTypes.CHANGE_ROOM,
                              val roomChanged: Boolean): ResponseInterface