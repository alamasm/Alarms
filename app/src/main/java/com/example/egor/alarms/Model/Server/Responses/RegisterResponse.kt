package Server.Responses

import Server.requests.RequestTypes

data class RegisterResponse(override val responseType: Int = RequestTypes.REGISTER,
                            val userID: Int,
                            val registred: Boolean): ResponseInterface