package com.example.egor.alarms.View.RoomView

import Server.User
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

import com.example.egor.alarms.R

class RoomUnapprovedUsersFragment : Fragment() {
    private lateinit var users: Array<User>
    private lateinit var messages: Array<String>

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UnapprovedUsersAdapter

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            users = it.getParcelableArray("users") as Array<User>
            messages = it.getStringArray("messages")
            adapter = UnapprovedUsersAdapter(users, messages)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_unapproved_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = getView()!!.findViewById<RecyclerView>(R.id.recycler_unapproved_users)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    fun updateUnapprovedUsers(unapprovedUsers: List<Pair<User, String>>) {
        val unUsers = ArrayList<User>()
        val messages = ArrayList<String>()
        for ((user, message) in unapprovedUsers) {
            unUsers.add(user)
            messages.add(message)
        }
        this.users = unUsers.toTypedArray()
        this.messages = messages.toTypedArray()
        updateData(this.users, this.messages)
    }

    private fun updateData(users: Array<User>, messages: Array<String>) {
        adapter.updateData(users, messages)
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
        fun newInstance(unapprovedUsers: List<Pair<User, String>>): RoomUnapprovedUsersFragment {
            val users = ArrayList<User>()
            val messages = ArrayList<String>()
            for ((user, s) in unapprovedUsers) {
                users.add(user)
                messages.add(s)
            }
            return RoomUnapprovedUsersFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray("users", users.toTypedArray())
                    putStringArray("messages", messages.toTypedArray())
                }
            }
        }
    }
}
