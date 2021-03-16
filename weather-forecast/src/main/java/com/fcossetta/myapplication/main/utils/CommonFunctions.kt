package com.fcossetta.myapplication.main.utils

class CommonFunctions {
    companion object Factory {
        fun formatTemp(date: Double?): String {
            val temp = date.toString()
            return "$temp°"
        }
    }

}