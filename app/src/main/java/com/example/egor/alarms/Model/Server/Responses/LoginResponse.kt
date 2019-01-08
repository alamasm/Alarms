package Server.Responses

import Server.requests.RequestTypes

data class LoginResponse(override val responseType: Int = RequestTypes.LOGIN,
                         val logged: Boolean, val userID: Int): ResponseInterface