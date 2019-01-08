package Server.requests

data class IsRequestToRoomAcceptedRequest(override val requestType: Int = RequestTypes.IS_REQUEST_IN_ROOM_ACCEPTED,
                                          override val address: String,
                                          override val userID: Int,
                                          override val userPassword: String,
                                          val roomID: Int): RequestInterface