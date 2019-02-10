package Server.requests

data class IsRequestToRoomSentRequest(
    override val requestType: Int = RequestTypes.REQUEST_TO_ROOM_SENT,
    override val address: String,
    override val userId: Int,
    override val userPassword: String,
    val roomId: Int
) : RequestInterface