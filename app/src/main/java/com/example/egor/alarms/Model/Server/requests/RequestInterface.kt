package Server.requests

interface RequestInterface {
    val requestType: Int
    val userID: Int
    val userPassword: String
    val address: String
}