package Server.requests

import Server.Responses.IsRequestToRoomSentResponse
import Server.Responses.ResponseInterface
import com.example.egor.alarms.Model.Server.Responses.AcceptRequestToRoomResponse
import com.example.egor.alarms.Model.Server.Responses.ResponseAdapter
import com.example.egor.alarms.Model.Server.requests.AcceptRequestToRoomRequest
import java.io.FileWriter
import com.google.gson.*
import org.json.JSONObject


fun main(args: Array<String>) {
    var request = IsRequestToRoomSentRequest(address = "", userId = 0, userPassword = "abc", roomId = 0)
    var json = gsonForRequest.toJson(request)
    var request1 =
        AcceptRequestToRoomRequest(address = "", userId = 0, userPassword = "abc", userToAcceptId = 10, roomId = 0)
    val response = IsRequestToRoomSentResponse(sent = true, roomId = 10)
    val response1 = AcceptRequestToRoomResponse(added = true)
    json = gsonForResponse.toJson(response1)
    print(json)
}


private val gsonForRequest =
    GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
private val gsonForResponse =
    GsonBuilder().registerTypeAdapter(ResponseInterface::class.java, ResponseAdapter()).create()