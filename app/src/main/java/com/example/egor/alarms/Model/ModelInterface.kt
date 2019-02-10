package com.example.egor.alarms.Model

import Server.Room
import com.example.egor.alarms.Model.DB.DBInterface

interface ModelInterface {
    suspend fun login(username: String, passwordHash: String): Int
    suspend fun register(username: String, passwordHash: String): Int
    suspend fun logout()
    suspend fun createRoom(room: Room): Boolean
    suspend fun changeRoom(room: Room): Boolean
    suspend fun getRoom(roomID: Int): Room
    suspend fun getRooms(userID: Int): List<Room>
    suspend fun searchRoom(name: String): List<Room>
    suspend fun sendRequestToRoom(roomID: Int, message: String): Boolean
    suspend fun isRequestToRoomSent(roomID: Int): Boolean
    fun logged(): Boolean
    fun getUsername(): String
    fun getPasswordHash(): String
    fun getUserID(): Int
}