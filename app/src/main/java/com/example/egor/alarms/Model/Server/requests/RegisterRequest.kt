package Server.requests


data class RegisterRequest(override val address: String,
                           override val requestType: Int = RequestTypes.REGISTER,
                           override val userId: Int = -1,
                           override val userPassword: String,
                           val userName: String): RequestInterface