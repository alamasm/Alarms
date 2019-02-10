package com.example.egor.alarms.View.RoomView

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.egor.alarms.Controller.ControllerSingleton
import com.example.egor.alarms.Model.Server.Alarm
import com.example.egor.alarms.R


class RoomAlarmsFragment : Fragment() {

    private lateinit var alarms: Array<Alarm>
    private var accepted = false

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlarmsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alarms = it.getParcelableArray("alarms") as Array<Alarm>
            accepted = it.getBoolean("sent")
            adapter = AlarmsAdapter(alarms, context!!, accepted)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_alarms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = getView()!!.findViewById<RecyclerView>(R.id.room_alarms_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    fun updateAlarms(alarms: List<Alarm>) {
        this.alarms = alarms.toTypedArray()
        updateData(this.alarms)
    }

    private fun updateData(alarms: Array<Alarm>) {
        adapter.updateData(alarms)
        adapter.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            RoomViewActivity.REMOVE_ALARM_CONTEXT -> ControllerSingleton.instance.onRoomViewRemoveAlarmClicked(adapter.currentLongClickAlarm)
            RoomViewActivity.CHANGE_ALARM_CONTEXT -> ControllerSingleton.instance.onRoomViewChangeAlarmClicked(adapter.currentLongClickAlarm)
        }
        return true
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(alarms: List<Alarm>, accepted: Boolean) =
            RoomAlarmsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray("alarms", alarms.toTypedArray())
                    putBoolean("sent", accepted)
                }
            }
    }
}
