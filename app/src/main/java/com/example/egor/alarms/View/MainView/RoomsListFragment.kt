package com.example.egor.alarms.View.MainView

import Server.Room
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.example.egor.alarms.R

class RoomsListFragment : Fragment() {
    private lateinit var rooms: Array<Room>
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RoomsAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rooms = it.getParcelableArray("rooms") as Array<Room>
            adapter = RoomsAdapter(rooms)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rooms_list, container, false)
    }

    fun updateRooms(rooms: List<Room>) {
        this.rooms = rooms.toTypedArray()
        updateData(this.rooms)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = getView()!!.findViewById(R.id.recycler_rooms_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        progressBar = getView()!!.findViewById(R.id.rooms_list_progress_bar)
        progressBar.visibility = ProgressBar.INVISIBLE
        recyclerView.adapter = adapter
    }

    fun startLoading() {
        recyclerView.visibility = RecyclerView.INVISIBLE
        progressBar.visibility = ProgressBar.VISIBLE
    }

    fun stopLoading() {
        progressBar.visibility = ProgressBar.INVISIBLE
        recyclerView.visibility = RecyclerView.VISIBLE
    }

    private fun updateData(rooms: Array<Room>) {
        adapter.updateData(rooms)
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
        fun newInstance(rooms: List<Room>) =
            RoomsListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray("rooms", rooms.toTypedArray())
                }
            }
    }
}
