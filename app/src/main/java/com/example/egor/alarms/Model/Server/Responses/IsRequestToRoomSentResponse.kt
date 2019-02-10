package Server.Responses

import Server.requests.RequestTypes

data class IsRequestToRoomSentResponse(
    override val responseType: Int = RequestTypes.REQUEST_TO_ROOM_SENT,
    val sent: Boolean,
    val roomId: Int
) : ResponseInterface