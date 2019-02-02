package Server

import android.os.Parcel
import android.os.Parcelable
import com.example.egor.alarms.Model.Server.Alarm

data class Room(
    var id: Int,
    var name: String,
    var adminId: Int,
    var unapprovedUsers: List<Pair<User, String>>,
    var users: List<User>,
    var alarms: List<Alarm>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        createUnapprovedUsers(parcel.createTypedArrayList(User), parcel.createStringArrayList()),
        parcel.createTypedArrayList(User),
        parcel.createTypedArrayList(Alarm)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        val unUsers = ArrayList<User>()
        val messages = ArrayList<String>()
        for ((user, message) in unapprovedUsers) {
            unUsers.add(user)
            messages.add(message)
        }
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(adminId)
        parcel.writeTypedList(unUsers)
        parcel.writeStringList(messages)
        parcel.writeTypedList(users)
        parcel.writeTypedList(alarms)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Room> {
        override fun createFromParcel(parcel: Parcel): Room {
            return Room(parcel)
        }

        override fun newArray(size: Int): Array<Room?> {
            return arrayOfNulls(size)
        }

        private fun createUnapprovedUsers(unUsers: List<User>, messages: List<String>): List<Pair<User, String>> {
            val unapprovedUsers = ArrayList<Pair<User, String>>()
            for (i in 0..unUsers.size) {
                unapprovedUsers.add(Pair(unUsers[i], messages[i]))
            }
            return unapprovedUsers
        }
    }
}