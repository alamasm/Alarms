package com.example.egor.alarms.View.RoomView

import Server.Room
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import com.example.egor.alarms.Controller.ControllerSingleton
import com.example.egor.alarms.Model.Server.Alarm
import com.example.egor.alarms.R
import com.example.egor.alarms.View.ActivitiesInterfaces.RoomViewActivityInterface
import khttp.request

import kotlinx.android.synthetic.main.activity_room_view.*

class RoomViewActivity : AppCompatActivity(), RoomViewActivityInterface {
    private lateinit var room: Room
    private var isAdmin = false
    private var accepted = false
    private var requestSent = false
    private lateinit var adapter: RoomViewPagerAdapter
    private var adapterInited = false

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton

    private var currentPageId = 0

    companion object {
        const val REMOVE_ALARM_CONTEXT = 0
        const val CHANGE_ALARM_CONTEXT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_view)
        setSupportActionBar(room_view_toolbar)

        fab = findViewById(R.id.room_view_fab)

        fab.setOnClickListener { view ->
            if (accepted) {
                when (currentPageId) {
                    0 -> ControllerSingleton.instance.onRoomViewActivityAddAlarmButtonClicked(this)
                }
            } else {
                if (!requestSent) ControllerSingleton.instance.onRoomViewActivitySendRequestClicked(this)
            }
        }
        progressBar = findViewById(R.id.room_progress_bar)
        progressBar.visibility = ProgressBar.INVISIBLE
        tabLayout = findViewById(R.id.room_view_tab_layout)
        viewPager = findViewById(R.id.room_view_pager)
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                currentPageId = p0
                if (accepted) {
                    if (currentPageId != 0) fab.hide()
                    else fab.show()
                }
            }
        })
        //val window = window

        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.setStatusBarColor(getColor(R.color.statusbar_color));
    }

    override fun onStart() {
        super.onStart()
        ControllerSingleton.instance.onRoomViewActivityStarted(this)
    }

    private fun initTabs() {
        room_view_toolbar.setTitle(room.name)
        if (!adapterInited) {
            adapter = RoomViewPagerAdapter(supportFragmentManager, isAdmin, room, accepted)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
            adapterInited = true
        } else {
            adapter.updateRoom(room)
            adapter.notifyDataSetChanged()
        }
        if (!accepted) {
            if (!requestSent) fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.send_fab))
            else fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.fab_wating_request))
        } else fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.icon_add))
    }


    override fun setRoom(room: Room) {
        this.room = room
    }

    override fun getRoom(): Room {
        return room
    }

    override fun getPageId(): Int {
        return currentPageId
    }

    override fun showAddAlarmDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.add_alarm_dialog, null)
        dialogBuilder.setView(dialogView)
        val timepicker = dialogView.findViewById<TimePicker>(R.id.add_alarm_timepicker)
        timepicker.setIs24HourView(true)
        val alarmName = dialogView.findViewById<EditText>(R.id.add_alarm_alarm_name)
        val buttons = getButtons(dialogView)
        dialogBuilder.setTitle("Create alarm")
        dialogBuilder.setPositiveButton("OK") { e, v ->
            e.dismiss()
            addAlarm(timepicker.hour, timepicker.minute, alarmName.text.toString(), buttons)
        }
        dialogBuilder.setNegativeButton("Cancel") { e, v -> e.dismiss() }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun showChangeAlarmDialog(alarm: Alarm) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.add_alarm_dialog, null)
        dialogBuilder.setView(dialogView)
        val timepicker = dialogView.findViewById<TimePicker>(R.id.add_alarm_timepicker)
        timepicker.setIs24HourView(true)
        val alarmName = dialogView.findViewById<EditText>(R.id.add_alarm_alarm_name)
        val buttons = getButtons(dialogView)
        alarmName.setText(alarm.name)
        timepicker.hour = alarm.time[0]
        timepicker.minute = alarm.time[1]
        for (i in 0 until 7) if (alarm.days[i] == '1') buttons[i].isChecked = true
        dialogBuilder.setTitle("Change alarm")
        dialogBuilder.setPositiveButton("OK") { e, v ->
            e.dismiss()
            changeAlarm(timepicker.hour, timepicker.minute, alarmName.text.toString(), buttons, alarm.id)
        }
        dialogBuilder.setNegativeButton("Cancel") { e, v -> e.dismiss() }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun startRequestDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.send_request_dialog, null)
        dialogBuilder.setView(dialogView)
        val message = dialogView.findViewById<EditText>(R.id.send_request_message_text)
        dialogBuilder.setTitle("Create new room")
        dialogBuilder.setPositiveButton("OK") { e, v ->
            e.dismiss()
            ControllerSingleton.instance.onRoomViewActivitySendRequestOKClicked(this, message.text.toString())
        }
        dialogBuilder.setNegativeButton("Cancel") { e, v -> e.dismiss() }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun getButtons(view: View): List<CheckBox> {
        val buttons = ArrayList<CheckBox>()
        buttons.add(view.findViewById(R.id.add_alarm_check_1))
        buttons.add(view.findViewById(R.id.add_alarm_check_2))
        buttons.add(view.findViewById(R.id.add_alarm_check_3))
        buttons.add(view.findViewById(R.id.add_alarm_check_4))
        buttons.add(view.findViewById(R.id.add_alarm_check_5))
        buttons.add(view.findViewById(R.id.add_alarm_check_6))
        buttons.add(view.findViewById(R.id.add_alarm_check_7))
        return buttons
    }

    private fun addAlarm(hours: Int, minutes: Int, alarmName: String, buttons: List<CheckBox>) {
        var repeat = ""
        for (button in buttons) {
            repeat += if (button.isChecked) "1" else "0"
        }
        ControllerSingleton.instance.onRoomViewAddAlarmOKClicked(
            this,
            Alarm(-1, alarmName, arrayOf(hours, minutes), repeat)
        )
    }

    private fun changeAlarm(hours: Int, minutes: Int, alarmName: String, buttons: List<CheckBox>, id: Int) {
        var repeat = ""
        for (button in buttons) {
            repeat += if (button.isChecked) "1" else "0"
        }
        ControllerSingleton.instance.onRoomViewChangeAlarmOKClicked(
            Alarm(
                id,
                alarmName,
                arrayOf(hours, minutes),
                repeat
            )
        )
    }

    override fun onInternetError() {
        Toast.makeText(applicationContext, "Check Internet connection", Toast.LENGTH_LONG).show()
    }

    override fun requestToRoomSent(sent: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startLoading() {
        viewPager.visibility = ViewPager.INVISIBLE
        progressBar.visibility = ProgressBar.VISIBLE

    }

    override fun stopLoading() {
        progressBar.visibility = ProgressBar.INVISIBLE
        viewPager.visibility = ViewPager.VISIBLE
    }

    override fun setAdmin(isAdmin: Boolean) {
        this.isAdmin = isAdmin
    }

    override fun setAccepted(accepted: Boolean) {
        this.accepted = accepted
    }

    override fun setRequest(requestSent: Boolean) {
        this.requestSent = requestSent
    }

    override fun updateTabs() {
        initTabs()
    }
}
