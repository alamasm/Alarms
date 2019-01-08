package Server.requests

data class AllUsersTurnedOffAlarmRequest(override val requestType: Int = RequestTypes.ALL_USERS_TURNED_OFF_ALARM,
                                         override val userID: Int,
                                         override val address: String,
                                         override val userPassword: String,
                                         val roomID: Int): RequestInterface