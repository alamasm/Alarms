package Server

import com.example.egor.alarms.Model.Server.Alarm

data class Room(val id: Int,
                val name: String,
                val adminID: Int,
                val unapprovedUsers: List<Pair<User, String>>,
                val users: List<User>,
                val alarms: List<Alarm>,
                val acceptedUser: Boolean)