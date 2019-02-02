package Server

import android.os.Parcel
import android.os.Parcelable

data class SimpleTime(val time: Array<Int>) : Parcelable {
    constructor(parcel: Parcel) : this(arrayOf(parcel.readInt(), parcel.readInt())) {
    }

    constructor(hours: Int, minutes: Int) : this(arrayOf(hours, minutes))

    val hours: Int get() = time[0]
    val minutes: Int get() = time[1]

    override fun toString(): String {
        return "${time[0]}:${time[1]}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(time[0])
        parcel.writeInt(time[1])
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimpleTime> {
        override fun createFromParcel(parcel: Parcel): SimpleTime {
            return SimpleTime(parcel)
        }

        override fun newArray(size: Int): Array<SimpleTime?> {
            return arrayOfNulls(size)
        }
    }
}