package Server.Responses

import Server.Room
import Server.requests.RequestTypes

data class GetRoomsResponse(override val responseType: Int = RequestTypes.GET_ROOMS,
                            val rooms: List<Room>): ResponseInterface