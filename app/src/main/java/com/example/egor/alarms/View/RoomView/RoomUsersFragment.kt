package com.example.egor.alarms.View.RoomView

import Server.Room
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
import com.example.egor.alarms.Controller.ControllerSingleton

import com.example.egor.alarms.R
import com.example.egor.alarms.View.ActivitiesInterfaces.UsersFragmentInterface
import kotlinx.android.synthetic.*

class RoomUsersFragment : Fragment(), UsersFragmentInterface {
    private lateinit var users: Array<User>
    private lateinit var adapter: UsersAdapter

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            users = it.getParcelableArray("users") as Array<User>
            adapter = UsersAdapter(users)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_room_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = getView()!!.findViewById<RecyclerView>(R.id.room_users_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        recyclerView.visibility = RecyclerView.VISIBLE
        adapter.updateData(users)
        ControllerSingleton.instance.updateUsers(this)
    }

    override fun getUsers(): Array<User> {
        return users
    }

    override fun updateUsers(users: Array<User>) {
        this.users = users
        adapter.updateData(users)
    }

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
        fun newInstance(users: List<User>) =
            RoomUsersFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray("users", users.toTypedArray())
                }
            }
    }
}
