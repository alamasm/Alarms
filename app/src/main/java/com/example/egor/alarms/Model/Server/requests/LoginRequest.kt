package Server.requests

data class LoginRequest(override val address: String,
                        override val requestType: Int = RequestTypes.LOGIN,
                        override val userID: Int = -1,
                        override val userPassword: String,
                        val username: String): RequestInterface