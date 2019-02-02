package com.example.egor.alarms.Model.Server

import Server.SimpleTime
import android.os.Parcel
import android.os.Parcelable

data class Alarm(var id: Int, var name: String, var time: Array<Int>, var days: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        arrayOf(parcel.readInt(), parcel.readInt()),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(time[0])
        parcel.writeInt(time[1])
        parcel.writeString(days)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alarm> {
        override fun createFromParcel(parcel: Parcel): Alarm {
            return Alarm(parcel)
        }

        override fun newArray(size: Int): Array<Alarm?> {
            return arrayOfNulls(size)
        }
    }

}