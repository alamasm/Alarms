package com.example.egor.alarms.View.RoomView

import Server.Room
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class RoomViewPagerAdapter(fm: FragmentManager, private val isAdmin: Boolean = false, private val room: Room) :
    FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? = when (p0) {
        0 -> RoomAlarmsFragment.newInstance(if (room.alarms != null) room.alarms else emptyList())
        1 -> RoomUsersFragment.newInstance(if (room.users != null) room.users else emptyList())
        2 -> RoomUnapprovedUsersFragment.newInstance(if (room.unapprovedUsers != null) room.unapprovedUsers else emptyList())
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Alarms"
        1 -> "Users"
        2 -> "Unapproved users"
        else -> ""
    }


    override fun getCount(): Int {
        return if (isAdmin) 3
        else 2
    }

}