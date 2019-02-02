package com.example.egor.alarms.View.MainView

import Server.Room
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.egor.alarms.Controller.ControllerSingleton
import com.example.egor.alarms.R
import com.example.egor.alarms.View.ActivitiesInterfaces.MainActivityInterface
import com.example.egor.alarms.View.LoginView.LoginActivity
import com.example.egor.alarms.View.RoomView.RoomViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainActivityInterface {
    private lateinit var roomsListFragment: RoomsListFragment
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: ImageButton

    private var searching = false

    override fun startLoginActivity() {
        startLoginOrRegisterActivity(false)
    }

    override fun startRegisterActivity() {
        startLoginOrRegisterActivity(true)
    }

    private fun startLoginOrRegisterActivity(register: Boolean) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(LoginActivity.REGISTER_INTENT_EXTRA_NAME, register)
        startActivity(intent)
    }

    override fun startRoomViewActivity(canChange: Boolean) {
        val intent = Intent(this, RoomViewActivity::class.java)
        startActivity(intent)
    }

    override fun updateRooms(rooms: List<Room>) {
        roomsListFragment.updateRooms(rooms)
    }

    override fun startLoading() {
        roomsListFragment.startLoading()
    }

    override fun stopLoading() {
        roomsListFragment.stopLoading()
    }

    override fun getSearchText(): String {
        return searchEditText.text.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        room_view_fab.setOnClickListener { view -> ControllerSingleton.instance.onMainActivityCreateRoomButtonClicked() }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, main_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        searchEditText = main_toolbar.findViewById(R.id.main_search_text)
        searchButton = main_toolbar.findViewById(R.id.main_search_button)
        searchEditText.visibility = EditText.INVISIBLE
        searchButton.setOnClickListener {
            if (!searching) {
                startSearchRoom()
            } else {
                clearSearchText()
            }
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchTextChanged()
            }
        })
        nav_view.setNavigationItemSelectedListener(this)
        initController()
        roomsListFragment = RoomsListFragment.newInstance(emptyList())
        setFragment(roomsListFragment)
    }

    override fun showCreateRoomDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.add_room_dialog, null)
        dialogBuilder.setView(dialogView)
        val roomNameEditText = dialogView.findViewById<EditText>(R.id.add_room_room_name_text)
        dialogBuilder.setTitle("Create new room")
        dialogBuilder.setPositiveButton("OK") { e, v ->
            e.dismiss()
            ControllerSingleton.instance.onMainActivityCreateRoomOKButtonClicked(roomNameEditText.text.toString())
        }
        dialogBuilder.setNegativeButton("Cancel") { e, v -> e.dismiss() }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun onStart() {
        super.onStart()
        ControllerSingleton.instance.onMainActivityStarted(this)
    }

    private fun startSearchRoom() {
        searching = true
        searchButton.setImageResource(R.drawable.icon_toolbar_clear)
        searchEditText.visibility = EditText.VISIBLE
        searchEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        hideFAB()
    }

    private fun hideFAB() {
        room_view_fab.hide()
    }

    private fun showFAB() {
        room_view_fab.show()
    }

    private fun clearSearchText() {
        searchEditText.setText("")
    }

    private fun searchTextChanged() {
        if (getSearchText() != "")
            ControllerSingleton.instance.onMainActivitySearchTextChanged()
    }

    private fun initController() {
        ControllerSingleton.init(applicationContext, this)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_view_layout, fragment).commit()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (searching) {
            searchButton.setImageResource(R.drawable.icon_toolbar_search)
            searchEditText.visibility = EditText.INVISIBLE
            searching = false
            ControllerSingleton.instance.onMainActivitySearchClosed()
            showFAB()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onInternetError() {
        Toast.makeText(applicationContext, "Check Internet connection", Toast.LENGTH_LONG).show()
    }
}
