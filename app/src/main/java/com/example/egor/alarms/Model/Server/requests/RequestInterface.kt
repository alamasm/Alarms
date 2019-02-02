package Server.requests

interface RequestInterface {
    val requestType: Int
    val userId: Int
    val userPassword: String
    val address: String
}