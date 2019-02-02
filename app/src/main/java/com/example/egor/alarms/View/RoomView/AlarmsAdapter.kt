package com.example.egor.alarms.View.RoomView

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.egor.alarms.Model.Server.Alarm
import com.example.egor.alarms.R
import kotlinx.android.synthetic.main.recycler_view_alarm_item.view.*

class AlarmsAdapter(private var alarms: Array<Alarm>) : RecyclerView.Adapter<AlarmsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var alarm: Alarm
        fun bind(alarm: Alarm) {
            itemView.recycler_view_alarm_name.text = alarm.name
            itemView.recycler_alarm_item_time_text_view.text = alarm.getTimeString()
            this.alarm = alarm
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.recycler_view_alarm_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(alarms[p1])
    }

    fun updateData(alarms: Array<Alarm>) {
        this.alarms = alarms
    }
}