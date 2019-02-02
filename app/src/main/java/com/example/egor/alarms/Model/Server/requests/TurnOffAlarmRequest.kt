package Server.requests

data class TurnOffAlarmRequest(override val address: String,
                               override val requestType: Int = RequestTypes.TURN_OFF_ALARM,
                               override val userId: Int,
                               override val userPassword: String,
                               val roomID: Int): RequestInterface