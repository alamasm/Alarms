package com.example.egor.alarms.Model.DB

import Server.Room
import Server.User
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import com.example.egor.alarms.Model.Server.Alarm

class AndroidDB(private val helper: SQLiteOpenHelper) : DBInterface {
    override fun saveRoom(room: Room) {
        for (user in room.users) saveUser(user)
        for (user in room.unapprovedUsers) saveUser(user)
        for (alarm in room.alarms) saveAlarm(alarm)

        helper.writableDatabase.insert(DBHelper.ROOMS_TABLE_NAME, null, getContentValues(room))
    }

    override fun changeRoom(room: Room) {
        for (user in room.users) updateUser(user)
        for (user in room.unapprovedUsers) updateUser(user)
        for (alarm in room.alarms) updateAlarm(alarm)
        helper.writableDatabase.update(
            DBHelper.ROOMS_TABLE_NAME,
            getContentValues(room),
            "id = ?",
            listOf(room.id.toString()).toTypedArray()
        )
    }

    override fun getRooms(): List<Room> {
        val cursor = helper.readableDatabase.rawQuery("SELECT * FROM ${DBHelper.ROOMS_TABLE_NAME}", null)
        if (!cursor.moveToFirst()) {
            return emptyList()
        }
        val rooms =  ArrayList<Room>()
        do {
            rooms.add(getRoom(cursor))
        } while (cursor.moveToNext())
        return rooms
    }

    private fun getRoom(cursor: Cursor): Room {
        val id = cursor.getInt(cursor.getColumnIndex("id"))
        val name = cursor.getString(cursor.getColumnIndex("name"))
        val usersIdsString = cursor.getString(cursor.getColumnIndex("users"))
        val unapprovedUsersIdsString = cursor.getString(cursor.getColumnIndex("unapproved_users"))
        val alarmsIdsString = cursor.getString(cursor.getColumnIndex("alarms"))
        val adminId = cursor.getInt(cursor.getColumnIndex("admin_id"))
        //val acceptedUser = cursor.getInt(cursor.getColumnIndex("accepted_user")) == 1

        val usersIds = getIdsListFromString(usersIdsString)
        val unapprovedUsersIds = getIdsListFromString(unapprovedUsersIdsString)
        val alarmsIds = getIdsListFromString(alarmsIdsString)

        val users = ArrayList<User>()
        for (userId in usersIds) users.add(getUser(userId))
        val unapprovedUsers = ArrayList<Pair<User, String>>()
        for (userId in unapprovedUsersIds) unapprovedUsers.add(getUnapprovedUser(userId))
        val alarms = ArrayList<Alarm>()
        for (alarmId in alarmsIds) alarms.add(getAlarm(alarmId))
        return Room(id, name, adminId, unapprovedUsers, users, alarms)
    }

    private fun getIdsListFromString(idsString: String): List<Int> {
        val s = idsString.split(";")
        return s.map { it.toInt() }
    }

    override fun updateRooms(rooms: List<Room>) {
        for (room in rooms) changeRoom(room)
    }

    override fun getRoom(id: Int): Room? {
        val cursor = helper.readableDatabase.rawQuery("SELECT * FROM ${DBHelper.ROOMS_TABLE_NAME} WHERE id = $id", null)
        return if (cursor.moveToFirst()) {
            getRoom(cursor)
        } else null
    }

    private fun saveUser(user: User) {
        helper.writableDatabase.insert(DBHelper.USERS_TABLE_NAME, null, getContentValues(user))
    }

    private fun updateUser(user: User) {
        helper.writableDatabase.update(
            DBHelper.USERS_TABLE_NAME,
            getContentValues(user),
            "id = ?",
            listOf(user.id.toString()).toTypedArray()
        )
    }

    private fun getUser(id: Int): User {
        val cursor = helper.readableDatabase.rawQuery("SELECT * FROM ${DBHelper.USERS_TABLE_NAME} WHERE id = $id", null)
        return getUser(cursor)
    }

    private fun getUser(cursor: Cursor): User {
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndex("id"))
        val username = cursor.getString(cursor.getColumnIndex("userName"))
        return User(username, id)
    }

    private fun getContentValues(user: User): ContentValues {
        val cv = ContentValues()
        cv.put("id", user.id)
        cv.put("userName", user.name)
        return cv
    }

    private fun saveUser(user: Pair<User, String>) {
        helper.writableDatabase.insert(DBHelper.USERS_TABLE_NAME, null, getContentValues(user))
    }

    private fun updateUser(user: Pair<User, String>) {
        helper.writableDatabase.update(
            DBHelper.USERS_TABLE_NAME,
            getContentValues(user),
            "id = ?",
            listOf(user.first.id.toString()).toTypedArray()
        )
    }

    private fun getUnapprovedUser(id: Int): Pair<User, String> {
        val cursor = helper.readableDatabase.rawQuery("SELECT * FROM ${DBHelper.USERS_TABLE_NAME} WHERE id = $id", null)
        return getUnapprovedUser(cursor)
    }

    private fun getUnapprovedUser(cursor: Cursor): Pair<User, String> {
        cursor.moveToFirst()
        val user = getUser(cursor)
        val unapprovedText = cursor.getString(cursor.getColumnIndex("unapproved_text"))
        return Pair(user, unapprovedText)
    }

    private fun getContentValues(user: Pair<User, String>): ContentValues {
        val cv = getContentValues(user.first)
        cv.put("unapproved_text", user.second)
        return cv
    }

    private fun saveAlarm(alarm: Alarm) {
        helper.writableDatabase.insert(DBHelper.ALARMS_TABLE_NAME, null, getContentValues(alarm))
    }

    private fun updateAlarm(alarm: Alarm) {
        helper.writableDatabase.update(
            DBHelper.ALARMS_TABLE_NAME,
            getContentValues(alarm),
            "id = ?",
            listOf(alarm.id.toString()).toTypedArray()
        )
    }

    private fun getAlarm(id: Int): Alarm {
        val cursor =
            helper.readableDatabase.rawQuery("SELECT * FROM ${DBHelper.ALARMS_TABLE_NAME} WHERE id = $id", null)
        return getAlarm(cursor)
    }

    private fun getAlarm(cursor: Cursor): Alarm {
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndex("id"))
        val name = cursor.getString(cursor.getColumnIndex("name"))
        val timeHours = cursor.getInt(cursor.getColumnIndex("time_hours"))
        val timeMinutes = cursor.getInt(cursor.getColumnIndex("time_minutes"))
        val repeatString = cursor.getString(cursor.getColumnIndex("days"))
        return Alarm(id, name, arrayOf(timeHours, timeMinutes), repeatString)
    }
    private fun getContentValues(alarm: Alarm): ContentValues {
        val cv = ContentValues()
        cv.put("id", alarm.id)
        cv.put("name", alarm.name)
        cv.put("time_hours", alarm.time[0])
        cv.put("time_minutes", alarm.time[1])
        return cv
    }

    private fun getContentValues(room: Room): ContentValues {
        val usersIds = ArrayList<Int>()
        for (user in room.users) usersIds.add(user.id)
        val unapprovedUsersIds = ArrayList<Int>()
        for (user in room.unapprovedUsers) unapprovedUsersIds.add(user.first.id)
        val userIdsString = getIDsAsString(usersIds)
        val unapprovedUsersIdsString = getIDsAsString(unapprovedUsersIds)
        val alarmsIds = ArrayList<Int>()
        for (alarm in room.alarms) alarmsIds.add(alarm.id)
        val alarmsIdsString = getIDsAsString(alarmsIds)
        val cv = ContentValues()
        cv.put("id", room.id)
        cv.put("admin_id", room.adminId)
        cv.put("users", userIdsString)
        cv.put("unapproved_users", unapprovedUsersIdsString)
        cv.put("alarms", alarmsIdsString)
        return cv
    }

    private fun getIDsAsString(ids: List<Int>): String {
        val res = StringBuilder()
        for (i in 0..(ids.size - 1)) {
            if (i != ids.size - 1) res.append("${ids[i]};")
            else res.append("${ids[i]}")
        }
        return res.toString()
    }
}