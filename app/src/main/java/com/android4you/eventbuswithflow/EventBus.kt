package com.android4you.eventbuswithflow

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBus {
    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    suspend fun emitEvent(event: Event) {
        Log.d(TAG, "Emitting event = $event")
        _events.emit(event)
    }

    companion object {
        private const val TAG = "EventBus"
    }
}