package com.example.egor.alarms.Model.Server

import Server.SimpleTime

data class Alarm(var id: Int, var name: String, var time: SimpleTime, var repeat: String) // daysWhenRepeat - массив с индексами дней когда нужно повторять будильник