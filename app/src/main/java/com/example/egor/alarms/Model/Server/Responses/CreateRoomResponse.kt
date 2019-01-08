package Server.Responses

import Server.requests.RequestTypes

data class CreateRoomResponse(override val responseType: Int = RequestTypes.CREATE_ROOM,
                              val roomID: Int,
                              val roomCreated: Boolean): ResponseInterface