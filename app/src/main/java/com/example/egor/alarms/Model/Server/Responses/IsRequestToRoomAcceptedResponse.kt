package Server.Responses

import Server.requests.RequestTypes

data class IsRequestToRoomAcceptedResponse(override val responseType: Int = RequestTypes.IS_REQUEST_IN_ROOM_ACCEPTED,
                                           val accepted: Boolean,
                                           val roomID: Int): ResponseInterface