package com.example.egor.alarms.View.RoomView

import Server.User
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.egor.alarms.R
import kotlinx.android.synthetic.main.recycler_view_unapproved_user_item.view.*

class UnapprovedUsersAdapter(private var users: Array<User>, private var messages: Array<String>) :
    RecyclerView.Adapter<UnapprovedUsersAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var user: User
        private lateinit var message: String

        fun bind(user: User, message: String) {
            itemView.recycler_unapproved_user_item_userame_text_view.text = user.name
            itemView.recycler_unapproved_user_item_message_text_view.text = message
            this.user = user
            this.message = message
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.recycler_view_unapproved_user_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(users[p1], messages[p1])
    }

    fun updateData(users: Array<User>, messages: Array<String>) {
        this.users = users
        this.messages = messages
    }
}