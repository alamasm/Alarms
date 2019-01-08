package Server.requests

data class SendRequestToRoomRequest(override val address: String,
                                    override val requestType: Int = RequestTypes.SEND_REQUEST_TO_ROOM,
                                    override val userID: Int,
                                    override val userPassword: String,
                                    val roomID: Int): RequestInterface