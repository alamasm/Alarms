package com.example.egor.alarms.Controller

import Server.Room
import com.example.egor.alarms.Model.Cryptography.CryptInterface
import com.example.egor.alarms.Model.ModelInterface
import com.example.egor.alarms.Model.Server.Alarm
import com.example.egor.alarms.View.ActivitiesInterfaces.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.lang.Thread.sleep
import java.net.ConnectException

class AndroidController(
    private val model: ModelInterface,
    private val crypter: CryptInterface,
    private val mainActivity: MainActivityInterface
) : ControllerInterface {

    private var currentRoomId: Int = 0

    override fun onMainActivityStarted(mainActivity: MainActivityInterface) {
        if (!model.logged()) mainActivity.startLoginActivity()
        else {
            print("LOGGED: " + model.logged())
            mainActivity.startLoading()
            launch {
                val rooms = model.getRooms(model.getUserID())
                launch(UI) {
                    mainActivity.updateRooms(rooms)
                    mainActivity.stopLoading()
                }
            }
        }
    }

    override fun onMainActivityRoomButtonClicked(roomID: Int) {
        currentRoomId = roomID
        mainActivity.startRoomViewActivity()
    }

    override fun onMainActivityFinished() {

    }

    override fun onMainActivitySearchTextChanged() {
        val searchText = mainActivity.getSearchText()
        mainActivity.startLoading()
        launch {
            val rooms = if (searchText != "") model.searchRoom(searchText) else model.getRooms(model.getUserID())
            launch(UI) {
                mainActivity.stopLoading()
                mainActivity.updateRooms(rooms)
            }
        }
    }

    override fun onMainActivitySearchClosed() {
        mainActivity.startLoading()
        launch {
            val rooms = model.getRooms(model.getUserID())
            launch(UI) {
                mainActivity.stopLoading()
                mainActivity.updateRooms(rooms)
            }
        }
    }

    override fun onMainActivityCreateRoomButtonClicked() {
        mainActivity.showCreateRoomDialog()
    }

    override fun onMainActivityCreateRoomOKButtonClicked(roomName: String) {
        mainActivity.startLoading()
        launch {
            model.createRoom(Room(-1, roomName, model.getUserID(), emptyList(), emptyList(), emptyList()))
            val rooms = model.getRooms(model.getUserID())
            launch(UI) {
                mainActivity.updateRooms(rooms)
                mainActivity.stopLoading()
            }
        }
    }

    override fun onRoomViewActivityStarted(roomViewActivity: RoomViewActivityInterface) {
        roomViewActivity.startLoading()
        launch {
            val currentRoom = model.getRoom(currentRoomId)
            launch(UI) {
                if (currentRoom.adminId == model.getUserID()) roomViewActivity.setAdmin(true)
                for (user in currentRoom.users) if (user.userId == model.getUserID()) roomViewActivity.setAccepted(true)
                roomViewActivity.setRoom(currentRoom)
                roomViewActivity.stopLoading()
            }
        }
    }

    override fun onRoomViewActivitySaveRoomClicked(roomViewActivity: RoomViewActivityInterface) {
        launch { model.createRoom(roomViewActivity.getRoom()) }
        roomViewActivity.finish()
    }

    override fun onRoomViewActivitySendRequestClicked(roomViewActivity: RoomViewActivityInterface) {
        roomViewActivity.startLoading()
        launch {
            val roomID = roomViewActivity.getRoom().id
            val res = model.sendRequestToRoom(roomID)
            launch(UI) {
                roomViewActivity.stopLoading()
                roomViewActivity.requestToRoomSent(res)
            }
        }
    }

    override fun onRoomViewActivityAddButtonClicked(roomViewActivity: RoomViewActivityInterface) {
        when (roomViewActivity.getPageId()) {
            0 -> roomViewActivity.showAddAlarmDialog()
        }
    }

    override fun onRoomViewAddAlarmOKClicked(roomViewActivity: RoomViewActivityInterface, alarm: Alarm) {
        roomViewActivity.startLoading()
        launch {
            var room = roomViewActivity.getRoom()
            val alarms: MutableList<Alarm>
            alarms = if (room.alarms == null) arrayListOf()
            else room.alarms.toMutableList()
            alarms.add(alarm)
            room.alarms = alarms
            room.users = emptyList()
            room.unapprovedUsers = emptyList()
            model.changeRoom(room)
            room = model.getRoom(room.id)//TODO
            launch(UI) {
                roomViewActivity.setRoom(room)
                roomViewActivity.stopLoading()
            }
        }
    }

    override fun onRoomViewFinished() {

    }

    override fun onLoginActivityStarted(loginActivity: LoginActivityInterface) {

    }

    override fun onLoginActivityLoginClicked(loginActivity: LoginActivityInterface) {
        loginActivity.startLoading()
        launch {
            val passwordHash = crypter.getHash(loginActivity.getPassword())
            try {
                val res = model.login(loginActivity.getUsername(), passwordHash)
                launch(UI) {
                    loginActivity.stopLoading()
                    if (res == -1) loginActivity.onLoginError("")
                    else loginActivity.onLoginOk()
                }
            } catch (e: ConnectException) {
                launch(UI) {
                    loginActivity.stopLoading()
                    loginActivity.onInternetError()
                }
            }
        }
    }

    override fun onLoginActivityRegisterClicked(loginActivity: LoginActivityInterface) {
        loginActivity.startLoading()
        launch {
            val passwordHash = crypter.getHash(loginActivity.getPassword())
            try {
                val res = model.register(loginActivity.getUsername(), passwordHash)
                launch(UI) {
                    loginActivity.stopLoading()
                    if (res == -1) loginActivity.onLoginError("")
                    else loginActivity.onLoginOk()
                }
            } catch (e: ConnectException) {
                launch(UI) {
                    loginActivity.stopLoading()
                    loginActivity.onInternetError()
                }

            }
        }
    }

    override fun onLoginActivityFinished() {

    }

    override fun onInternetError() {
        launch(UI) {
            mainActivity.onInternetError()
        }
    }

    override fun updateUsers(usersFragmentInterface: UsersFragmentInterface) {
        launch {
            delay(10000)
            launch(UI) {
                usersFragmentInterface.updateUsers(usersFragmentInterface.getUsers())
            }
        }
    }
}