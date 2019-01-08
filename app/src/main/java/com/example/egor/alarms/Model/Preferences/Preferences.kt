package com.example.pektu.lifecounter.Model.Preferences

interface Preferences {
    companion object {
        const val LOGGED_TAG = "logged"
        const val USERNAME_TAG = "username"
        const val PASSWORD_HASH_TAG = "password_hash"
        const val USER_ID_TAG = "user_id"
    }

    fun saveInt(tag: String, i: Int)
    fun saveString(tag: String, s: String)
    fun saveBoolean(tag: String, b: Boolean)

    fun getInt(tag: String): Int
    fun getString(tag: String): String
    fun getBoolean(tag: String): Boolean
}