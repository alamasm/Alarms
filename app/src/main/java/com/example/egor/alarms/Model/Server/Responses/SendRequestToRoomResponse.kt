package Server.Responses

import Server.requests.RequestTypes

data class SendRequestToRoomResponse(override val responseType: Int = RequestTypes.SEND_REQUEST_TO_ROOM,
                                     val requestSent: Boolean): ResponseInterface