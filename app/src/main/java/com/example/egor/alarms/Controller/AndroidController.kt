package com.example.egor.alarms.Controller

import Server.Room
import Server.ServerInterface
import Server.requests.main
import com.example.egor.alarms.Model.Cryptography.CryptInterface
import com.example.egor.alarms.Model.DB.DBInterface
import com.example.egor.alarms.View.ActivitiesInterfaces.*
import com.example.egor.alarms.View.MainActivity
import com.example.pektu.lifecounter.Model.Preferences.Preferences
import kotlinx.coroutines.*;
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class AndroidController(
    private val db: DBInterface, private val prefs: Preferences, private val server: ServerInterface,
    private val crypter: CryptInterface, private val mainActivity: MainActivityInterface
) : ControllerInterface {
    var currentActivity: Activity = mainActivity
    var currentActivityType = ActivityTypes.main
    var userID = 0
    lateinit var username: String
    lateinit var passwordHash: String

    override fun onMainActivityLoaded(mainActivityInterface: MainActivityInterface) {
        if (!prefs.getBoolean(Preferences.LOGGED_TAG)) mainActivityInterface.startLoginActivity()
        else {
            launch {
                userID = prefs.getInt(Preferences.USER_ID_TAG)
                passwordHash = prefs.getString(Preferences.PASSWORD_HASH_TAG)
                username = prefs.getString(Preferences.USERNAME_TAG)
            }
        }
    }

    override fun onLoginActivityStarted(loginActivity: LoginActivity) {
        currentActivity = loginActivity
        currentActivityType = ActivityTypes.login
    }

    override suspend fun onLoginResult(logged: Boolean, userID: Int, username: String, passwordHash: String) {
        if (currentActivityType == ActivityTypes.login) {
            when (logged) {
                true -> {
                    val save = async {
                        prefs.saveInt(Preferences.USER_ID_TAG, userID)
                        prefs.saveString(Preferences.USERNAME_TAG, username)
                        prefs.saveString(Preferences.PASSWORD_HASH_TAG, passwordHash)
                        prefs.saveBoolean(Preferences.LOGGED_TAG, true)
                    }
                    launch(UI) {
                        (currentActivity as LoginActivity).stopLoading()
                        (currentActivity as LoginActivity).onLoginOk()
                    }
                    save.await()
                }
                false -> {
                    launch(UI) {
                        (currentActivity as LoginActivity).stopLoading()
                        (currentActivity as LoginActivity).onLoginError("")
                    }
                }
            }
        }
    }

    override suspend fun onRegisterResult(registred: Boolean, userID: Int, username: String, passwordHash: String) {
        if (currentActivityType == ActivityTypes.login) {
            when (registred) {
                true -> {
                    val save = async {
                        prefs.saveInt(Preferences.USER_ID_TAG, userID)
                        prefs.saveString(Preferences.USERNAME_TAG, username)
                        prefs.saveString(Preferences.PASSWORD_HASH_TAG, passwordHash)
                        prefs.saveBoolean(Preferences.LOGGED_TAG, true)
                    }
                    launch(UI) {
                        (currentActivity as LoginActivity).stopLoading()
                        (currentActivity as LoginActivity).onRegisterOK()
                    }
                    save.await()
                }
                false -> {
                    launch(UI) {
                        (currentActivity as LoginActivity).stopLoading()
                        (currentActivity as LoginActivity).onRegisterError("")
                    }
                }
            }
        }
    }

    override suspend fun onCreateRoomResult(created: Boolean, roomID: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onChangeRoomResult(changed: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onGetRoomsResult(rooms: List<Room>) {
        val save = async { db.updateRooms(rooms) }
        launch(UI) {
            mainActivity.updateRooms(rooms)
        }
        save.await()
    }

    override suspend fun onSendRequestToRoomResult(requestSent: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onTurnOffAlarmResult(turnedOff: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onAllUserTurnedAlarmOffResult(allUsersOffAlarm: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onGetRoomResult(room: Room) {
        val save = async { db.changeRoom(room) }
        launch(UI) { mainActivity.startRoomViewActivity(room) }
        save.await()
    }

    override suspend fun onSearchResult(rooms: List<Room>) {
        if (currentActivityType == ActivityTypes.searchRoom) {
            launch(UI) { (currentActivity as SearchRoomActivity).updateRooms(rooms) }
        }
    }

    override fun onLoginClicked(loginActivity: LoginActivity) {
        val username = loginActivity.getUsername()
        val password = loginActivity.getPassword()
        val controller = this
        loginActivity.startLoading()
        launch {
            val passwordHash = crypter.getHash(password)
            server.login(controller, username, passwordHash)
        }
    }

    override fun onRegisterClicked(loginActivity: LoginActivity) {
        val username = loginActivity.getUsername()
        val password = loginActivity.getPassword()
        val controller = this
        launch {
            val passwordHash = crypter.getHash(password)
            server.register(controller, username, passwordHash)
        }
    }

    override fun onMainActivityClose(mainActivityInterface: MainActivityInterface) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMainActivityRoomClicked(mainActivityInterface: MainActivityInterface, roomID: Int) {
        val controller = this
        launch { server.getRoom(controller, userID, passwordHash, roomID) }
    }

    override fun onMainActivitySearchButtonClicked(mainActivityInterface: MainActivityInterface) {
        mainActivity.startSearchRoomActivity()
    }

    override fun onMainActivityAddButtonClicked(mainActivityInterface: MainActivityInterface) {
        mainActivity.startRoomViewActivity(canChange = true)
    }

    override fun onSearchActivityTextChanged(searchRoomActivity: SearchRoomActivity, text: String) {
        val controller = this
        launch {
            server.searchRoom(controller, userID, passwordHash, text)
        }
    }

    override fun onLoginActivityFinished() {
        currentActivity = mainActivity
        currentActivityType = ActivityTypes.main
    }

    override fun onSearchRoomActivityStarted(searchRoomActivity: SearchRoomActivity) {
        currentActivity = searchRoomActivity
        currentActivityType = ActivityTypes.searchRoom
    }

    override fun onSearchRoomActivityFinished() {
        currentActivity = mainActivity
        currentActivityType = ActivityTypes.main
    }

    override fun onRoomViewActivityStarted(roomViewActivity: RoomViewActivity) {
        currentActivity = roomViewActivity
        currentActivityType = ActivityTypes.roomView
    }

    override fun onRoomViewActivityFinished() {
        currentActivity = mainActivity
        currentActivityType = ActivityTypes.main
    }
}