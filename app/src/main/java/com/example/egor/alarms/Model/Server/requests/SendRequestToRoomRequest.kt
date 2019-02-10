package Server.requests

data class SendRequestToRoomRequest(override val address: String,
                                    override val requestType: Int = RequestTypes.SEND_REQUEST_TO_ROOM,
                                    override val userId: Int,
                                    override val userPassword: String,
                                    val roomId: Int,
                                    val msg: String
) : RequestInterface