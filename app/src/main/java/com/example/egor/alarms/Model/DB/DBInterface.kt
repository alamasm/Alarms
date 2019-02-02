package com.example.egor.alarms.Model.DB

import Server.Room

interface DBInterface {
    fun saveRoom(room: Room)
    fun changeRoom(room: Room)
    fun getRooms(): List<Room>
    fun updateRooms(rooms: List<Room>)
    fun getRoom(id: Int): Room?
}