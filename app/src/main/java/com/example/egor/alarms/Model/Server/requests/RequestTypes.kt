package Server.requests

class RequestTypes {
    companion object {
        const val REGISTER = 0
        const val LOGIN = 1
        const val CREATE_ROOM = 2
        const val CHANGE_ROOM = 3
        const val GET_ROOMS = 4
        const val SEND_REQUEST_TO_ROOM = 5
        const val TURN_OFF_ALARM = 6
        const val SEARCH_ROOM = 7
        const val ALL_USERS_TURNED_OFF_ALARM = 8
        const val REQUEST_TO_ROOM_SENT = 9
        const val GET_ROOM = 10
        const val ACCEPT_REQUEST_TO_ROOM = 11
    }
}