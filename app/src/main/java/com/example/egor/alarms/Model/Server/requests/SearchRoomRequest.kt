package Server.requests

data class SearchRoomRequest(override val address: String,
                             override val requestType: Int = RequestTypes.SEARCH_ROOM,
                             override val userId: Int,
                             override val userPassword: String,
                             val roomName: List<String>
) : RequestInterface {
    constructor(address: String, userId: Int, userPassword: String, roomName: String) : this(
        address,
        userId = userId,
        userPassword = userPassword,
        roomName = listOf(roomName)
    )
}