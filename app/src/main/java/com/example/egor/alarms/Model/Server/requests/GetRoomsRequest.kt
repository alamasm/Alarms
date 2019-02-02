package Server.requests

data class GetRoomsRequest(override val address: String,
                           override val requestType: Int = RequestTypes.GET_ROOMS,
                           override val userId: Int,
                           override val userPassword: String): RequestInterface