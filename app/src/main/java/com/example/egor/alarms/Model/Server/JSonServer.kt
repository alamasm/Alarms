import Server.Responses.*
import Server.Room
import Server.ServerInterface
import Server.requests.*
import com.example.egor.alarms.Controller.ControllerInterface
import com.example.egor.alarms.Model.Server.Responses.GetRoomResponse
import com.example.egor.alarms.Model.Server.Responses.ResponseAdapter
import com.example.egor.alarms.Model.Server.requests.GetRoomReqest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import khttp.*
import khttp.responses.Response

class JSonServer: ServerInterface {
    override suspend fun register(controller: ControllerInterface, username: String, passwordHash: String) {
        val registerRequest = RegisterRequest("", RequestTypes.REGISTER, -1, passwordHash, username)
        val responseJson = (sendPost(registerRequest) as RegisterResponse)
        controller.onRegisterResult(responseJson.registred, responseJson.userID, username, passwordHash)
    }

    override suspend fun login(controller: ControllerInterface, username: String, passwordHash: String) {
        val loginRequest = LoginRequest("", RequestTypes.LOGIN, -1, passwordHash, username)
        val responseJson = sendPost(loginRequest) as LoginResponse
        controller.onLoginResult(responseJson.logged, responseJson.userID, username, passwordHash)
    }

    override suspend fun createRoom(controller: ControllerInterface, userId: Int, passwordHash: String, room: Room) {
        val createRoomRequest = CreateRoomRequest("", userID = userId, userPassword = passwordHash, room = room)
        val responseJson = sendPost(createRoomRequest) as CreateRoomResponse
        controller.onCreateRoomResult(responseJson.roomCreated, responseJson.roomID)
    }

    override suspend fun changeRoom(controller: ControllerInterface, userId: Int, passwordHash: String, newRoom: Room) {
        val changeRoomRequest = ChangeRoomRequest("", userID = userId, userPassword = passwordHash, newRoom = newRoom)
        val responseJson = sendPost(changeRoomRequest) as ChangeRoomResponse
        controller.onChangeRoomResult(responseJson.roomChanged)
    }

    override suspend fun getRooms(controller: ControllerInterface, userID: Int, passwordHash: String) {
        val getRoomsRequest = GetRoomsRequest("", userID = userID, userPassword = passwordHash)
        val responseJson = sendPost(getRoomsRequest) as GetRoomsResponse
        controller.onGetRoomsResult(responseJson.rooms)
    }

    override suspend fun searchRoom(
        controller: ControllerInterface,
        usedID: Int,
        passwordHash: String,
        roomName: String
    ) {
        val searchRoomRequest = SearchRoomRequest("", userID = usedID, userPassword = passwordHash, roomName = roomName)
        val responseJson = sendPost(searchRoomRequest) as SearchRoomResponse
        controller.onSearchRoomResult(responseJson.rooms)
    }

    override suspend fun sendRequestToRoom(
        controller: ControllerInterface,
        userId: Int,
        passwordHash: String,
        roomId: Int
    ) {
        val sendRequestToRoomRequest =
            SendRequestToRoomRequest("", userID = userId, userPassword = passwordHash, roomID = roomId)
        val responseJson = sendPost(sendRequestToRoomRequest) as SendRequestToRoomResponse
        controller.onSendRequestToRoomResult(responseJson.requestSent)
    }

    override suspend fun allUsersTurnedAlarmOff(
        controller: ControllerInterface,
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    ) {
        val allUsersTurnedOffAlarmRequest =
            AllUsersTurnedOffAlarmRequest(userID = userId, roomID = roomId, address = "", userPassword = passwordHash)
        val responseJson = sendPost(allUsersTurnedOffAlarmRequest) as AllUsersTurnedOffAlarmResponse
        controller.onAllUserTurnedAlarmOffResult(responseJson.allUsersTurnedOff)
    }

    override suspend fun turnOffAlarm(
        controller: ControllerInterface,
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    ) {
        val turnOffAlarmRequest = TurnOffAlarmRequest("", userID = userId, userPassword = passwordHash, roomID = roomId)
        val responseJson = sendPost(turnOffAlarmRequest) as TurnOffAlarmResponse
        controller.onTurnOffAlarmResult(responseJson.turnedOff)
    }

    override suspend fun getRoom(controller: ControllerInterface, userId: Int, passwordHash: String, roomId: Int) {
        val getRoomRequest = GetRoomReqest("", userID = userId, userPassword = passwordHash, roomID = roomId)
        val responseJson = sendPost(getRoomRequest) as GetRoomResponse
        controller.onGetRoomResult(responseJson.room)
    }

    private fun sendPost(request: RequestInterface): ResponseInterface {
        val json = JSONObject(gsonForRequest.toJson(request).toString())
        val r = post(ServerInterface.URL, json = json).text
        return gsonForResponse.fromJson(r, ResponseInterface::class.java)
    }


    private val gsonForRequest: Gson by lazy {
        GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
    }

    private val gsonForResponse: Gson by lazy {
        GsonBuilder().registerTypeAdapter(Response::class.java, ResponseAdapter()).create()
    }
}