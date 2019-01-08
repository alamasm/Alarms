package com.example.pektu.lifecounter.Model.Preferences

import android.content.SharedPreferences

class AndroidPreferences(private val sharedPreferences: SharedPreferences): Preferences {
    override fun saveInt(tag: String, i: Int) {
        sharedPreferences.edit().putInt(tag, i).apply()
    }

    override fun saveString(tag: String, s: String) {
        sharedPreferences.edit().putString(tag, s).apply()
    }

    override fun saveBoolean(tag: String, b: Boolean) {
        sharedPreferences.edit().putBoolean(tag, b).apply()
    }

    override fun getInt(tag: String): Int {
        return sharedPreferences.getInt(tag, Preferences.INT_IN_PREFS_DEFAULT_VALUE)
    }

    override fun getString(tag: String): String {
        return sharedPreferences.getString(tag, Preferences.STRING_IN_PREFS_DEFAULT_VALUE)
    }

    override fun getBoolean(tag: String): Boolean {
        return sharedPreferences.getBoolean(tag, Preferences.BOOL_IN_PREFS_DEFAULT_VALUE)
    }

}