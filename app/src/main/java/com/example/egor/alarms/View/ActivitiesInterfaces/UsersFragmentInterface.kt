package com.example.egor.alarms.View.ActivitiesInterfaces

import Server.Room
import Server.User

interface UsersFragmentInterface {
    fun updateUsers(users: Array<User>)
    fun getUsers(): Array<User>
}