package com.example.egor.alarms.Model.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, CURRENT_VERSION) {
    companion object {
        const val DB_NAME = "alarms_db"
        const val CURRENT_VERSION = 1

        const val ROOMS_TABLE_NAME = "rooms"
        const val USERS_TABLE_NAME = "users"
        const val ALARMS_TABLE_NAME = "alarms"

        private const val CREATE_ROOMS_TABLE_SQL = "CREATE TABLE $ROOMS_TABLE_NAME ( id INTEGER PRIMARY KEY, " +
                "name TEXT, admin_id INTEGER, users TEXT, unapproved_users TEXT, alarms TEXT, accepted_user INTEGER );"

        private const val CREATE_ALARMS_TABLE_SQL = "CREATE TABLE $ALARMS_TABLE_NAME ( id INTEGER PRIMARY KEY, " +
                "name TEXT, time_hours INTEGER, time_minutes INTEGER, repeat TEXT );"

        private const val CREATE_USERS_TABLE_SQL = "CREATE TABLE $USERS_TABLE_NAME ( id INTEGER PRIMARY KEY, " +
                "username TEXT, unapproved_text TEXT );"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_ROOMS_TABLE_SQL)
        db.execSQL(CREATE_ALARMS_TABLE_SQL)
        db.execSQL(CREATE_USERS_TABLE_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}