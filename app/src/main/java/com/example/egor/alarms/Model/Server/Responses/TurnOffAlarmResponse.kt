package Server.Responses

import Server.requests.RequestTypes

data class TurnOffAlarmResponse(override val responseType: Int = RequestTypes.TURN_OFF_ALARM,
                                val turnedOff: Boolean): ResponseInterface