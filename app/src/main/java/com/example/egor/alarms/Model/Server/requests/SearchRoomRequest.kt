package Server.requests

data class SearchRoomRequest(override val address: String,
                             override val requestType: Int = RequestTypes.SEARCH_ROOM,
                             override val userId: Int,
                             override val userPassword: String,
                             val roomName: String
) : RequestInterface {

}