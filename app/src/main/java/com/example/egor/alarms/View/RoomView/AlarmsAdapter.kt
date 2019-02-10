package com.example.egor.alarms.View.RoomView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.egor.alarms.Model.Server.Alarm
import com.example.egor.alarms.R
import kotlinx.android.synthetic.main.recycler_view_alarm_item.view.*

class AlarmsAdapter(private var alarms: Array<Alarm>, private val context: Context, private val accepted: Boolean) :
    RecyclerView.Adapter<AlarmsAdapter.ViewHolder>() {

    lateinit var currentLongClickAlarm: Alarm

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        private lateinit var alarm: Alarm
        fun bind(alarm: Alarm) {
            itemView.recycler_view_alarm_name.text = alarm.name
            itemView.recycler_alarm_item_time_text_view.text = alarm.getTimeString()
            if (alarm.days[0] == '0') itemView.alarm_day_1.backgroundTintList = context.getColorStateList(R.color.white)
            if (alarm.days[1] == '0') itemView.alarm_day_2.backgroundTintList = context.getColorStateList(R.color.white)
            if (alarm.days[2] == '0') itemView.alarm_day_3.backgroundTintList = context.getColorStateList(R.color.white)
            if (alarm.days[3] == '0') itemView.alarm_day_4.backgroundTintList = context.getColorStateList(R.color.white)
            if (alarm.days[4] == '0') itemView.alarm_day_5.backgroundTintList = context.getColorStateList(R.color.white)
            if (alarm.days[5] == '0') itemView.alarm_day_6.backgroundTintList = context.getColorStateList(R.color.white)
            if (alarm.days[6] == '0') itemView.alarm_day_7.backgroundTintList = context.getColorStateList(R.color.white)
            this.alarm = alarm
            itemView.setOnLongClickListener { onLongClick() }
            itemView.setOnCreateContextMenuListener(this)
        }

        private fun onLongClick(): Boolean {
            currentLongClickAlarm = alarm
            itemView.showContextMenu()
            return true
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            if (accepted) {
                menu!!.add(Menu.NONE, RoomViewActivity.CHANGE_ALARM_CONTEXT, 0, "Change")
                menu.add(Menu.NONE, RoomViewActivity.REMOVE_ALARM_CONTEXT, 0, "Remove")
            }
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