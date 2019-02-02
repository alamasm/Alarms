package Server.Responses

import Server.requests.RequestTypes

data class AllUsersTurnedOffAlarmResponse(override val responseType: Int = RequestTypes.ALL_USERS_TURNED_OFF_ALARM,
                                          val allUsersTurnedOff: Boolean,
                                          val roomId: Int
) : ResponseInterface