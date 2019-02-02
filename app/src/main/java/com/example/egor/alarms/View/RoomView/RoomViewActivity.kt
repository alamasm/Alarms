package com.example.egor.alarms.View.RoomView

import Server.Room
import Server.SimpleTime
import android.os.Bundle
import android.support.design.widget.TabLayout
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

import kotlinx.android.synthetic.main.activity_room_view.*

class RoomViewActivity : AppCompatActivity(), RoomViewActivityInterface {
    private lateinit var room: Room
    private var isAdmin = false

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var progressBar: ProgressBar

    private var currentPageId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_view)
        setSupportActionBar(room_view_toolbar)

        room_view_fab.setOnClickListener { view ->
            ControllerSingleton.instance.onRoomViewActivityAddButtonClicked(this)
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
            }
        })
    }

    override fun onStart() {
        super.onStart()
        ControllerSingleton.instance.onRoomViewActivityStarted(this)
    }

    private fun initTabs() {
        val adapter = RoomViewPagerAdapter(supportFragmentManager, isAdmin, room)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setToolbarTitle(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.room_view_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
    }

    override fun setRoom(room: Room) {
        this.room = room
        initTabs()
        setToolbarTitle(room.name)
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
        val roomName = dialogView.findViewById<EditText>(R.id.add_alarm_alarm_name)
        val buttons = getButtons(dialogView)
        dialogBuilder.setTitle("Create alarm")
        dialogBuilder.setPositiveButton("OK") { e, v ->
            e.dismiss()
            addAlarm(timepicker.hour, timepicker.minute, roomName.text.toString(), buttons)
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

    private fun addAlarm(hours: Int, minutes: Int, roomName: String, buttons: List<CheckBox>) {
        var repeat = ""
        for (button in buttons) {
            repeat += if (button.isChecked) "1" else "0"
        }
        ControllerSingleton.instance.onRoomViewAddAlarmOKClicked(
            this,
            Alarm(-1, roomName, arrayOf(hours, minutes), repeat)
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
}
