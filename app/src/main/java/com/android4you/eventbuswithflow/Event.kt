package com.android4you.eventbuswithflow

sealed class Event {
    data class MessageEvent(val message: String) : Event()
    // Add more event types as needed
}