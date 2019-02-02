package com.example.egor.alarms.Model

import Server.Room
import Server.ServerInterface
import com.example.egor.alarms.Controller.ControllerSingleton
import com.example.egor.alarms.Model.DB.DBInterface
import com.example.pektu.lifecounter.Model.Preferences.Preferences
import kotlinx.coroutines.experimental.async
import java.io.IOException
import java.net.ConnectException

class AndroidModel(
    private val db: DBInterface, private val prefs: Preferences,
    private val server: ServerInterface
) : ModelInterface {
    private var userID = -1
    private var passwordHash = ""
    private var username = ""

    init {
        userID = prefs.getInt(Preferences.USER_ID_TAG)
        passwordHash = prefs.getString(Preferences.PASSWORD_HASH_TAG)
        username = prefs.getString(Preferences.USERNAME_TAG)
    }


    override suspend fun login(username: String, passwordHash: String): Int {
        val res = server.login(username, passwordHash)
        if (res == null) {
            throw ConnectException()
        }
        return if (res.logged) {
            prefs.saveBoolean(Preferences.LOGGED_TAG, true)
            prefs.saveString(Preferences.USERNAME_TAG, username)
            prefs.saveString(Preferences.PASSWORD_HASH_TAG, passwordHash)
            prefs.saveInt(Preferences.USER_ID_TAG, res.userId)
            this.userID = res.userId
            this.passwordHash = passwordHash
            this.username = username
            res.userId
        } else -1
    }

    override suspend fun register(username: String, passwordHash: String): Int {
        val res = server.register(username, passwordHash)
        if (res == null) {
            throw ConnectException()
        }
        return if (res.registred) {
            prefs.saveBoolean(Preferences.LOGGED_TAG, true)
            prefs.saveString(Preferences.USERNAME_TAG, username)
            prefs.saveString(Preferences.PASSWORD_HASH_TAG, passwordHash)
            prefs.saveInt(Preferences.USER_ID_TAG, res.userId)
            this.userID = res.userId
            this.passwordHash = passwordHash
            this.username = username
            res.userId
        } else -1
    }

    override suspend fun createRoom(room: Room): Boolean {
        val res = server.createRoom(userID, passwordHash, room)
        if (res == null) {
            ControllerSingleton.instance.onInternetError()
            return false
        }
        if (!res.roomCreated) return false
        room.id = res.roomId
        db.saveRoom(room)
        return res.roomCreated
    }

    override suspend fun changeRoom(room: Room): Boolean {
        val changeInDB = async { db.changeRoom(room) }
        val res = server.changeRoom(userID, passwordHash, room)
        changeInDB.await()
        if (res == null) {
            ControllerSingleton.instance.onInternetError()
            return false
        }
        return res.roomChanged
    }

    override suspend fun getRoom(roomID: Int): Room {
        val res = server.getRoom(userID, passwordHash, roomID)
        if (res == null) {
            val room = db.getRoom(roomID)
            ControllerSingleton.instance.onInternetError()
            if (room == null) throw IOException()
            else return room
        }
        async { saveOrUpdateRoom(res.room) }
        return res.room
    }

    override suspend fun getRooms(userID: Int): List<Room> {
        val res = server.getRooms(userID, passwordHash)
        if (res == null) {
            val rooms = db.getRooms()
            ControllerSingleton.instance.onInternetError()
            return rooms
        }
        async { for (room in res.rooms) saveOrUpdateRoom(room) }
        return res.rooms
    }

    private fun saveOrUpdateRoom(room: Room) {
        if (db.getRoom(room.id) != null) {
            db.changeRoom(room)
        } else {
            db.saveRoom(room)
        }
    }

    override suspend fun searchRoom(name: String): List<Room> {
        val res = server.searchRoom(userID, passwordHash, name)
        if (res == null) {
            val rooms = db.getRooms()
            ControllerSingleton.instance.onInternetError()
            return rooms
        }
        return res.rooms
    }

    override suspend fun sendRequestToRoom(roomID: Int): Boolean {
        val res = server.sendRequestToRoom(userID, passwordHash, roomID)
        if (res == null) {
            ControllerSingleton.instance.onInternetError()
            return false
        }
        return res.requestSent
    }

    override fun logged(): Boolean {
        return prefs.getBoolean(Preferences.LOGGED_TAG)
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPasswordHash(): String {
        return passwordHash
    }

    override fun getUserID(): Int {
        return userID
    }
}