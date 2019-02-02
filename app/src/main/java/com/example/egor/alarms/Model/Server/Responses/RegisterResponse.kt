package Server.Responses

import Server.requests.RequestTypes

data class RegisterResponse(override val responseType: Int = RequestTypes.REGISTER,
                            val userId: Int,
                            val registred: Boolean): ResponseInterface