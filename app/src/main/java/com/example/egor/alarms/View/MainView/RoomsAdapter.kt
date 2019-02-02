package com.example.egor.alarms.View.MainView

import Server.Room
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.egor.alarms.Controller.ControllerSingleton
import com.example.egor.alarms.R
import kotlinx.android.synthetic.main.recycler_view_room_item.view.*

class RoomsAdapter(private var rooms: Array<Room>) : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var room: Room
        fun bind(room: Room) {
            itemView.recycler_room_item_room_name_text_view.text = room.name
            this.room = room
            itemView.setOnClickListener {
                ControllerSingleton.instance.onMainActivityRoomButtonClicked(room.id)
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.recycler_view_room_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(rooms[p1])
    }

    fun updateData(rooms: Array<Room>) {
        this.rooms = rooms
        notifyDataSetChanged()
    }
}