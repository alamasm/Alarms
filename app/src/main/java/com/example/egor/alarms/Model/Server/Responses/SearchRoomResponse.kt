package Server.Responses

import Server.Room
import Server.requests.RequestTypes

data class SearchRoomResponse(override val responseType: Int = RequestTypes.SEARCH_ROOM,
                              val rooms: List<Room>): ResponseInterface