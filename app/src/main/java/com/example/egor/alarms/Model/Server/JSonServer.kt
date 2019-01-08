import Server.Responses.ResponseInterface
import Server.Room
import Server.ServerInterface
import Server.requests.RegisterRequest
import Server.requests.RequestAdapter
import Server.requests.RequestInterface
import Server.requests.RequestTypes
import com.example.egor.alarms.Controller.ControllerInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import khttp.*

class JSonServer: ServerInterface {
    override fun register(controller: ControllerInterface, username: String, passwordHash: String) {
        val registerRequest = RegisterRequest("", RequestTypes.REGISTER, -1, passwordHash, username)

        val r = post(ServerInterface.URL, json = JSONObject(gsonForRequest.toJson(registerRequest).toString())).text
        val resJson = gsonForRequest.fromJson(r, ResponseInterface::class.java)
        resJson as
    }

    override fun login(controller: ControllerInterface, userId: Int, passwordHash: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRoom(controller: ControllerInterface, userId: Int, passwordHash: String, room: Room) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeRoom(controller: ControllerInterface, userId: Int, passwordHash: String, newRoom: Room) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRooms(controller: ControllerInterface, userID: Int, passwordHash: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchRoom(controller: ControllerInterface, usedID: Int, passwordHash: String, roomName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendRequestToRoom(controller: ControllerInterface, userId: Int, passwordHash: String, roomId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun turnOffAlarm(
        controller: ControllerInterface,
        userId: Int,
        passwordHash: String,
        roomId: Int,
        alarmId: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val gsonForRequest: Gson by lazy {
        GsonBuilder().registerTypeAdapter(RequestInterface::class.java, RequestAdapter()).create()
    }
}